package com.kodilla.price.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodilla.price.domain.AmazonOfferDto;
import com.kodilla.price.entity.AmazonOffer;
import com.kodilla.price.entity.User;
import com.kodilla.price.mapper.AmazonMapper;
import com.kodilla.price.mapper.UserMapper;
import com.kodilla.price.repository.AmazonDao;
import com.kodilla.price.repository.UserDao;
import org.hamcrest.Matchers;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AmazonControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AmazonMapper amazonMapper;

    @Autowired
    private AmazonDao amazonDao;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDao userDao;


    @Test
    public void addProductTest() throws Exception {
        //Given
        AmazonOffer amazonOffer = AmazonOffer.builder()
                .asin("addProduct")
                .productName("product name")
                .currentPrice(BigDecimal.valueOf(1))
                .locale("US")
                .targetPrice(BigDecimal.valueOf(2))
                .currencySymbol("$")
                .build();
        User user = User.builder()
                .name("addProduct")
                .lastName("last name")
                .mail("test@test.com")
                .login("login")
                .password("password")
                .isActive(true)
                .build();
        User savedUser = userDao.save(user);

        //When & Then

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/v1/amazon/")
                                .param("id", "B005YQZ1KE")
                                .param("userId", String.valueOf(savedUser.getId()))
                                .param("targetPrice", String.valueOf(BigDecimal.valueOf(9998)))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

        //CleanUp
        AmazonOffer foundOffer = amazonDao.findByTargetPrice(BigDecimal.valueOf(9998)).orElse(null);
        amazonDao.deleteById(foundOffer.getId());
        userDao.deleteById(savedUser.getId());
    }

    @Test
    public void deleteOfferTest() throws Exception {
        //Given
        User user = User.builder()
                .name("deleteOffer")
                .lastName("last name")
                .mail("test@test.com")
                .login("login")
                .password("password")
                .isActive(true)
                .build();

        AmazonOffer amazonOffer = AmazonOffer.builder()
                .asin("deleteOffer")
                .productName("product name")
                .currentPrice(BigDecimal.valueOf(1))
                .locale("US")
                .targetPrice(BigDecimal.valueOf(2))
                .currencySymbol("$")
                .build();

        amazonOffer.getUserEntityList().add(user);
        user.getAmazonOfferList().add(amazonOffer);
        AmazonOffer savedOffer = amazonDao.save(amazonOffer);
        User foundUser = savedOffer.getUserEntityList().get(0);


        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .delete("/v1/amazon/deleteOffer/{id}", savedOffer.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
        userDao.deleteById(foundUser.getId());


    }

    @Test
    public void updateOfferTest() throws Exception {
        //Given
        ObjectMapper objectMapper = new ObjectMapper();

        AmazonOffer amazonOffer = AmazonOffer.builder()
                .asin("updateOffer")
                .productName("product name")
                .currentPrice(BigDecimal.valueOf(1))
                .locale("US")
                .targetPrice(BigDecimal.valueOf(2))
                .currencySymbol("$")
                .build();

        User user = User.builder()
                .name("updateOffer")
                .lastName("last name")
                .mail("test@test.com")
                .login("login")
                .password("password")
                .isActive(true)
                .build();
        amazonOffer.getUserEntityList().add(user);
        AmazonOffer savedOffer = amazonDao.save(amazonOffer);
        savedOffer.setCurrentPrice(BigDecimal.valueOf(9999));

        AmazonOfferDto amazonOfferDto = amazonMapper.mapToAmazonDto(savedOffer);
        String json = objectMapper.writeValueAsString(amazonOfferDto);
        User foundUser = savedOffer.getUserEntityList().get(0);

        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/v1/amazon/updateOffer/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current_price", Matchers.is(9999)))
                .andExpect(MockMvcResultMatchers.status().is(200));
        //CleanUp
        amazonDao.deleteById(savedOffer.getId());
        userDao.deleteById(foundUser.getId());

    }

    @Test
    public void getAllOffersTest() throws Exception {
        //Given
        User user = User.builder()
                .name("getAllOffers")
                .lastName("last name")
                .mail("test@test.com")
                .login("login")
                .password("password")
                .isActive(true)
                .build();
        AmazonOffer amazonOffer = AmazonOffer.builder()
                .asin("getAllOffers")
                .productName("product name")
                .currentPrice(BigDecimal.valueOf(1))
                .locale("US")
                .targetPrice(BigDecimal.valueOf(2))
                .currencySymbol("$")
                .build();
        amazonOffer.getUserEntityList().add(user);
        AmazonOffer savedOffer = amazonDao.save(amazonOffer);
        User foundUser = savedOffer.getUserEntityList().get(0);

        //When & Then

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/amazon/getOffers/")
                                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].asin", Matchers.is("getAllOffers")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].product_name", Matchers.is("product name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].current_price", Matchers.is(1.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].locale", Matchers.is("US")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].targetPrice", Matchers.is(2.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].currency_symbol", Matchers.is("$")));
        amazonDao.deleteById(savedOffer.getId());
        userDao.deleteById(foundUser.getId());
    }

    @Test
    public void refreshPriceTest() throws Exception {
        //Given
        AmazonOffer amazonOffer = AmazonOffer.builder()
                .asin("refreshPrice")
                .productName("product name")
                .currentPrice(BigDecimal.valueOf(1))
                .locale("US")
                .targetPrice(BigDecimal.valueOf(2))
                .currencySymbol("$")
                .build();
        AmazonOffer savedOffer = amazonDao.save(amazonOffer);


        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/v1/amazon/refreshPrice/{id}", savedOffer.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
        amazonDao.deleteById(savedOffer.getId());
    }

    @Test
    public void refreshPricesTest() throws Exception {
        //Given
        AmazonOffer amazonOffer = AmazonOffer.builder()
                .asin("refreshPrices")
                .productName("product name")
                .currentPrice(BigDecimal.valueOf(1))
                .locale("US")
                .targetPrice(BigDecimal.valueOf(2))
                .currencySymbol("$")
                .build();
        AmazonOffer savedOffer = amazonDao.save(amazonOffer);

        //When & Then

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/v1/amazon/refreshPrices/")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
        amazonDao.deleteById(savedOffer.getId());
    }

    @Test
    public void getOfferTest() throws Exception {
        //Given
        AmazonOffer amazonOffer = AmazonOffer.builder()
                .asin("getOffer")
                .productName("product name")
                .currentPrice(BigDecimal.valueOf(1))
                .locale("US")
                .targetPrice(BigDecimal.valueOf(2))
                .currencySymbol("$")

                .build();

        AmazonOffer savedOffer = amazonDao.save(amazonOffer);
        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/amazon/getOffer/{id}", savedOffer.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
        amazonDao.deleteById(savedOffer.getId());
    }


}
