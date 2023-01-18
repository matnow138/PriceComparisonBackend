package com.kodilla.price.scheduler;


import com.kodilla.price.domain.NbpDto;
import com.kodilla.price.entity.AmazonOffer;
import com.kodilla.price.entity.Currency;
import com.kodilla.price.mapper.CurrencyMapper;
import com.kodilla.price.repository.AmazonDao;
import com.kodilla.price.repository.CurrencyDao;
import com.kodilla.price.service.NbpService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
@Getter
@RequiredArgsConstructor
public class CheckPrices {

    private final NbpService nbpService;
    private final AmazonDao amazonDao;
    private final CurrencyDao currencyDao;
    private final CurrencyMapper currencyMapper;


    private final List<String> currencies1 = List.of("USD", "EUR", "PLN");

    private final Map<String, String> currencyConversion = new HashMap<>();

    public BigDecimal updateCurrencies(String currency) throws Exception {
        NbpDto nbpDto = nbpService.getCurrency(currency);
        return nbpDto.getRates().get(0).getAsk();

    }


    public void checkTargetPrice() throws Exception {
        Currency currency = currencyDao.findCurrencyByCurrency("$");

        BigDecimal exchangeRate = updateCurrencies(currency.getCurrency());
        List<AmazonOffer> filteredOffers = amazonDao.findAllByCurrencySymbol(currencyConversion.get(currency.getCurrencySymbol()));
        List<AmazonOffer> discountedOffers = filteredOffers.stream()
                .filter(p -> ((p.getCurrentPrice().multiply(exchangeRate)).compareTo(p.getTargetPrice()) < 0))
                .collect(Collectors.toList());
        if (discountedOffers.size() > 0) {
            System.out.println("mail");
        }
        System.out.println(discountedOffers);
    }


}
