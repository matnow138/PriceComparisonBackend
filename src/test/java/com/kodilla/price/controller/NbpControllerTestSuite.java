package com.kodilla.price.controller;

import com.kodilla.price.entity.Currency;
import com.kodilla.price.entity.Nbp;
import com.kodilla.price.repository.CurrencyDao;
import com.kodilla.price.repository.NbpDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NbpControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NbpDao nbpDao;
    @Autowired
    private CurrencyDao currencyDao;

    @AfterEach
    public void cleanUp() {
        currencyDao.deleteAll();
    }

    @Test
    public void getExchangeRateTest() throws Exception {
        //Given
        Nbp nbp = Nbp.builder()
                .currency("USD")
                .exchangeRate(BigDecimal.valueOf(1.0))
                .addedDate(LocalDate.now())
                .build();
        Nbp savedNbp = nbpDao.save(nbp);


        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/nbp/getExchangeRate/{currency}", savedNbp.getCurrency())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void addCurrencyTest() throws Exception {
        //Given

        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/v1/nbp/")
                                .characterEncoding("UTF-8")
                                .param("currency", "USD")
                                .param("currencySymbol", "$")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void getCurrencyName() throws Exception {
        //Given
        Currency currency = Currency.builder()
                .currencySymbol("$")
                .currency("USD")
                .build();
        currencyDao.save(currency);

        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/nbp/getCurrencyName/{currencySymbol}", currency.getCurrencySymbol())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void getCurrencySymbol() throws Exception {
        //Given
        Currency currency = Currency.builder()
                .currencySymbol("$")
                .currency("USD")
                .build();
        currencyDao.save(currency);

        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/nbp/getCurrencySymbol/{currencyName}", currency.getCurrency())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

}
