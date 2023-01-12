package com.kodilla.price.scheduler;

import com.kodilla.price.entity.AmazonOffer;
import com.kodilla.price.mailer.Mailer;
import com.kodilla.price.repository.AmazonDao;
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
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@EnableScheduling
@Configuration
public class AmazonScheduler {
    private final AmazonDao amazonDao;
    private final AmazonService amazonService;

    private final CheckPrices checkPrices;
    private final Mailer mailer;

    private final Logger logger = LoggerFactory.getLogger(AmazonScheduler.class);

    @Scheduled(fixedRate = 3600000)
    public void checkAmazonPromotions() throws Exception {
        List<AmazonOffer> allOffersWithSubscriptions = amazonDao.getAll();
        logger.debug(allOffersWithSubscriptions.get(1).getAsin());
        List<AmazonOffer> changedOffers = allOffersWithSubscriptions.stream()
                .filter(this::currentPriceIsLower)
                .collect(toList());
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

    private boolean isLower(BigDecimal original, BigDecimal current){
        return original.compareTo(current) < 0;
    }

    public Map<String, BigDecimal> getActualCurrencies(List<AmazonOffer> changedOffers) throws Exception {
        checkPrices.addCurrency();
        Map<String, String> currencies = checkPrices.getCurrencyConversion();
        Map<String, BigDecimal> currencyExchange = new HashMap<>();
        List<String> currencySymbols = changedOffers.stream()
                .map(AmazonOffer::getCurrencySymbol)
                .distinct().toList();

        for (int i = 0; i < currencySymbols.size(); i++) {
            logger.debug(currencySymbols.get(i));
            logger.debug(currencies.get(currencySymbols.get(i)));
            logger.debug(""+checkPrices.updateCurrencies(currencies.get(currencySymbols.get(i))));
            currencyExchange.put(currencySymbols.get(i), checkPrices.updateCurrencies(currencies.get(currencySymbols.get(i))));
        }
        logger.debug("{}", currencyExchange);
        return currencyExchange;
    }

    //do nowej klasy
    public void checkSubscribedPrices(List<AmazonOffer> changedOffers) throws Exception {

        Map<String, BigDecimal> currencyExchange = getActualCurrencies(changedOffers);
        Map<String, List<Subscription>> allSubscriptions = null; //subscriptionsRepository.getFor(changedOffersIds); --> map<offerId, List<subscr>)

        for(AmazonOffer changedOffer: changedOffers){
            List<Subscription> subscriptions = allSubscriptions.get(changedOffer.getAsin());
            for (Subscription subscription: subscriptions){
                checkSubscription(currencyExchange, changedOffer, subscription);
            }
        }
    }

    private void checkSubscription(Map<String, BigDecimal> currencyExchange, AmazonOffer changedOffer, Subscription subscription) throws URISyntaxException {
        MonetaryAmount offerPriceInTargetCurrency = toTargetCurrency(currencyExchange, changedOffer, subscription.alertPrice.getCurrency());
        if (offerPriceInTargetCurrency.isLessThan(subscription.alertPrice)){
            logger.debug("Sending mail with new price {} for offer {}", offerPriceInTargetCurrency, changedOffer.getAsin());
            mailer.sendAlert(Collections.singletonList(changedOffer));
        }
    }

    private static MonetaryAmount toTargetCurrency(Map<String, BigDecimal> currencyExchange, AmazonOffer offerInOriginalCurrency, CurrencyUnit targetCurrency) {
        BigDecimal amount = offerInOriginalCurrency.getCurrentPrice().multiply(currencyExchange.get(targetCurrency.getCurrencyCode()));
        return FastMoney.of(amount, targetCurrency);
    }


    record Subscription(String offerId, MonetaryAmount alertPrice){}


}
