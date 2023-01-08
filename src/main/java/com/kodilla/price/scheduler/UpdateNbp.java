package com.kodilla.price.scheduler;


import com.kodilla.price.config.Currencies;
import com.kodilla.price.domain.NbpDto;
import com.kodilla.price.entity.Amazon;
import com.kodilla.price.repository.AmazonDao;
import com.kodilla.price.repository.NbpDao;
import com.kodilla.price.service.NbpService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class UpdateNbp {

    private final NbpService nbpService;
    private final AmazonDao amazonDao;

    private List<String> currencies = List.of("USD","EUR","PLN");

    private Map<String,String> currencyConversion = new HashMap<>();

    public BigDecimal updateCurrencies(String currency) throws Exception{
        NbpDto nbpDto = nbpService.getCurrency(currency);
        return nbpDto.getRates().get(0).getAsk();

    }

    public void addCurrency(){
        currencyConversion.put(currencies.get(0),"$");
    }
    @Scheduled(fixedDelay = 1000000)
    public void checkTargetPrice()throws Exception{
        addCurrency();
        BigDecimal exchangeRate = updateCurrencies(currencies.get(0));

        List<Amazon> filteredOffers = amazonDao.findAllByCurrencySymbol(currencyConversion.get(currencies.get(0)));
        System.out.println(exchangeRate);
        System.out.println(filteredOffers.get(0).getAsin());
        List<Amazon> discountedOffers = filteredOffers.stream()
                .filter(p -> ((p.getCurrentPrice().multiply(exchangeRate)).compareTo(p.getTargetPrice())<0))
                .collect(Collectors.toList());
        if(discountedOffers.size()>0){
            System.out.println("mail");
        }
        System.out.println(discountedOffers);
    }


}
