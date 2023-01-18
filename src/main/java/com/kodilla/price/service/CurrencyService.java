package com.kodilla.price.service;

import com.kodilla.price.domain.CurrencyDto;
import com.kodilla.price.entity.Currency;
import com.kodilla.price.mapper.CurrencyMapper;
import com.kodilla.price.repository.CurrencyDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyDao currencyDao;
    private final CurrencyMapper currencyMapper;

    public void addCurrency(String currencySymbol, String currency) {
        if(currencyDao.findCurrencyByCurrency(currency)!=null){
            CurrencyDto currencyDto = CurrencyDto.builder()
                    .currency(currency)
                    .currencySymbol(currencySymbol)
                    .build();
            Currency currencyEntity = currencyMapper.mapToCurrency(currencyDto);
            currencyDao.save(currencyEntity);
        }
    }

    public String getCurrencyName(String currencySymbol) {
        Currency currency = currencyDao.findCurrencyByCurrencySymbol(currencySymbol);
        return currency.getCurrency();
    }

    public String getCurrencySymbol(String currencyName) {
        Currency currency = currencyDao.findCurrencyByCurrency(currencyName);
        return currency.getCurrencySymbol();
    }
}
