package com.kodilla.price.scheduler;

import com.kodilla.price.entity.AmazonOffer;
import com.kodilla.price.entity.User;
import com.kodilla.price.mailer.Mailer;
import com.kodilla.price.repository.AmazonDao;
import com.kodilla.price.repository.CurrencyDao;
import com.kodilla.price.service.AmazonService;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.FastMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@EnableScheduling
@Configuration
public class AmazonScheduler {
    private final AmazonDao amazonDao;
    private final AmazonService amazonService;

    private final CheckPrices checkPrices;
    private final Mailer mailer;
    private final Logger logger = LoggerFactory.getLogger(AmazonScheduler.class);
    private final CurrencyDao currencyDao;

    private static MonetaryAmount toTargetCurrency(Map<String, BigDecimal> currencyExchange, AmazonOffer offerInOriginalCurrency, CurrencyUnit targetCurrency) {
        BigDecimal amount = offerInOriginalCurrency.getCurrentPrice().multiply(currencyExchange.get(targetCurrency.getCurrencyCode()));
        return FastMoney.of(amount, targetCurrency);
    }

    @Scheduled(fixedRate = 3600000)
    public void checkAmazonPromotions() throws Exception {
        List<AmazonOffer> allOffersWithSubscriptions = amazonDao.getAll();
        logger.debug(allOffersWithSubscriptions.toString());
        List<AmazonOffer> changedOffers = allOffersWithSubscriptions.stream()
                .filter(this::currentPriceIsLower)
                .collect(Collectors.toList());
        logger.debug("Changed offers size: {}", changedOffers.size());
        if (!changedOffers.isEmpty()) {
            checkSubscribedPrices(changedOffers);
        }
    }

    private boolean currentPriceIsLower(AmazonOffer offer) {
        try {
            BigDecimal currentPrice = amazonService.getOffer(offer.getAsin()).getCurrentPrice();
            return isLower(offer.getCurrentPrice(), currentPrice);
        } catch (Exception e) {
            logger.warn("Problem loading offer: ", e);
            return false;
        }
    }

    private boolean isLower(BigDecimal original, BigDecimal current) {
        return original.compareTo(current) < 0;
    }

    public Map<AmazonOffer, List<User>> findUserAndOffer(List<AmazonOffer> discountedOffers) {
        Map<AmazonOffer, List<User>> offersForUsers = new HashMap<>();
        for (AmazonOffer amazonOffer : discountedOffers) {
            offersForUsers.put(amazonOffer, amazonOffer.getUserEntityList());
        }
        return offersForUsers;
    }

    public Map<String, BigDecimal> getActualCurrencies(List<AmazonOffer> changedOffers) throws Exception {
        Map<String, String> currencies = checkPrices.getCurrencyConversion();
        Map<String, BigDecimal> currencyExchange = new HashMap<>();
        List<String> currencySymbols = changedOffers.stream()
                .map(AmazonOffer::getCurrencySymbol)
                .distinct()
                .collect(Collectors.toList());

        for (int i = 0; i < currencySymbols.size(); i++) {
            logger.debug("Currency Exchange for offer {}", changedOffers.get(i).getAsin());
            currencyExchange.put(currencySymbols.get(i), checkPrices.updateCurrencies(currencyDao.findCurrencyByCurrencySymbol(currencySymbols.get(i)).getCurrency()));
        }
        return currencyExchange;
    }

    public void checkSubscribedPrices(List<AmazonOffer> changedOffers) throws Exception {

        Map<String, BigDecimal> currencyExchange = getActualCurrencies(changedOffers);


        List<AmazonOffer> discountedOffers = changedOffers.stream()
                .filter(a -> a.getCurrentPrice().multiply(currencyExchange.get(a.getCurrencySymbol())).compareTo(a.getTargetPrice()) < 0)
                .collect(Collectors.toList());
        Map<AmazonOffer, List<User>> offersForUsers = findUserAndOffer(discountedOffers);

        if (!discountedOffers.isEmpty()) {
            mailer.sendAlert(discountedOffers, offersForUsers);
        }


    }


}
