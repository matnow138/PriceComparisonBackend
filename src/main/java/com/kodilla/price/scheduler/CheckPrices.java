package com.kodilla.price.scheduler;


import org.javamoney.moneta.Money;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.NumberValue;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import java.util.Locale;

@Configuration
@EnableScheduling
public class CheckPrices {

    @Scheduled(fixedDelay = 1000)
    public void test(){

    }
}
