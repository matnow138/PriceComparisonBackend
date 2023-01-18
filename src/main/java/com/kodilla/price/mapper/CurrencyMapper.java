package com.kodilla.price.mapper;

import com.kodilla.price.domain.CurrencyDto;
import com.kodilla.price.entity.Currency;
import org.springframework.stereotype.Service;

@Service
public class CurrencyMapper {

    public Currency mapToCurrency(CurrencyDto currencyDto) {
        return Currency.builder()
                .id(currencyDto.getId())
                .currency(currencyDto.getCurrency())
                .currencySymbol(currencyDto.getCurrencySymbol())
                .build();
    }

    public CurrencyDto mapToCurrencyDto(Currency currency) {
        return CurrencyDto.builder()
                .id(currency.getId())
                .currency(currency.getCurrency())
                .currencySymbol(currency.getCurrencySymbol())
                .build();
    }


}
