package com.kodilla.price.repository;

import com.kodilla.price.entity.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyDao extends CrudRepository<Currency,String> {

    public Currency findCurrencyByCurrency(String currency);

    public Currency findCurrencyByCurrencySymbol(String currencySymbol);

}
