package com.kodilla.price.scheduler;

import com.kodilla.price.entity.AmazonOffer;
import com.kodilla.price.mailer.Mailer;
import com.kodilla.price.repository.AmazonDao;
import com.kodilla.price.service.AmazonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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

    @Scheduled(fixedRate = 3600000)
    public void checkAmazonPromotions() throws Exception {
        List<AmazonOffer> allOffersWithSubscriptions = amazonDao.getAll();
        System.out.println(allOffersWithSubscriptions.get(1).getAsin());
        List<AmazonOffer> changedOffers = allOffersWithSubscriptions.stream()
                .filter(a ->
                        {
                            try {
                                return a.getCurrentPrice().compareTo(amazonService.getOffer(a.getAsin()).getCurrentPrice()) <0;
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .collect(Collectors.toList());
        System.out.println(changedOffers.size());
        if (changedOffers.size() > 0) {
            checkTargetPrice(changedOffers);
        }
    }

    //public Map<Long, Long> findUser

    public Map<String, BigDecimal> getActualCurrencies(List<AmazonOffer> changedOffers) throws Exception {
        checkPrices.addCurrency();
        Map<String, String> currencies = checkPrices.getCurrencyConversion();
        Map<String, BigDecimal> currencyExchange = new HashMap<>();
        List<String> currencySymbols = changedOffers.stream()
                .map(AmazonOffer::getCurrencySymbol)
                .distinct().toList();

        for (int i = 0; i < currencySymbols.size(); i++) {
            System.out.println(currencySymbols.get(i));
            System.out.println(currencies.get(currencySymbols.get(i)));
            System.out.println(checkPrices.updateCurrencies(currencies.get(currencySymbols.get(i))));
            currencyExchange.put(currencySymbols.get(i), checkPrices.updateCurrencies(currencies.get(currencySymbols.get(i))));
        }
        System.out.println(currencyExchange);
        return currencyExchange;
    }

    public void checkTargetPrice(List<AmazonOffer> changedOffers) throws Exception {

        Map<String, BigDecimal> currencyExchange = getActualCurrencies(changedOffers);

        List<AmazonOffer> discountedOffers = changedOffers.stream()
                .filter(a -> a.getCurrentPrice().multiply(currencyExchange.get(a.getCurrencySymbol())).compareTo(a.getTargetPrice()) < 0)
                .toList();

        if (discountedOffers.size() > 0) {
            mailer.sendAlert(discountedOffers);
        }


    }


}
