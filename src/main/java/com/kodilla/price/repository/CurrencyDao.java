package com.kodilla.price.repository;

import com.kodilla.price.entity.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyDao extends CrudRepository<Currency, Long> {

    Currency findCurrencyByCurrency(String currency);

    Currency findCurrencyByCurrencySymbol(String currencySymbol);



    void deleteById(long id);
    void deleteByCurrency(String currency);

}
