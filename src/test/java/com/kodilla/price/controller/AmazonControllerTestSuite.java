package com.kodilla.price.controller;

import com.google.gson.Gson;
import com.kodilla.price.AmazonController;
import com.kodilla.price.domain.AmazonOfferDto;
import com.kodilla.price.domain.UserDto;
import com.kodilla.price.entity.AmazonOffer;
import com.kodilla.price.entity.User;
import com.kodilla.price.mapper.AmazonMapper;
import com.kodilla.price.mapper.UserMapper;
import com.kodilla.price.repository.AmazonDao;
import com.kodilla.price.repository.UserDao;
import org.hamcrest.Matchers;
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

    @AfterEach
    public void cleanUp(){
        amazonDao.deleteAll();
    }
    @Test
    public void addProductTest() throws Exception{
        //Given
        AmazonOfferDto amazonOfferDto = AmazonOfferDto.builder()
                .asin("asin")
                .product_name("product name")
                .currentPrice(BigDecimal.valueOf(1))
                .locale("US")
                .targetPrice(BigDecimal.valueOf(2))
                .currency_symbol("$")
                .build();
        UserDto userDto = UserDto.builder()
                .name("name")
                .lastName("last name")
                .mail("test@test.com")
                .login("login")
                .password("password")
                .isActive(true)
                .build();
        User user = userMapper.mapToUser(userDto);
        User savedUser = userDao.save(user);
        AmazonOffer amazonOffer = amazonMapper.mapToAmazon(amazonOfferDto);
        AmazonOffer savedOffer = amazonDao.save(amazonOffer);

        //When & Then

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/v1/amazon/")
                                .param("id","B005YQZ1KE")
                                .param("userID", String.valueOf(savedUser.getId()))
                                .param("targetPrice", String.valueOf(BigDecimal.valueOf(12)))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deleteOfferTest() throws Exception{
        //Given
        AmazonOfferDto amazonOfferDto = AmazonOfferDto.builder()
                .asin("asin")
                .product_name("product name")
                .currentPrice(BigDecimal.valueOf(1))
                .locale("US")
                .targetPrice(BigDecimal.valueOf(2))
                .currency_symbol("$")
                .build();
        UserDto userDto = UserDto.builder()
                .name("name")
                .lastName("last name")
                .mail("test@test.com")
                .login("login")
                .password("password")
                .isActive(true)
                .build();
        User user = userMapper.mapToUser(userDto);
        User savedUser = userDao.save(user);
        AmazonOffer amazonOffer = amazonMapper.mapToAmazon(amazonOfferDto);
        AmazonOffer savedOffer = amazonDao.save(amazonOffer);



        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/v1/amazon/deleteOffer/{id}", savedOffer.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }
    @Test
    public void updateOfferTest() throws Exception{
        //Given
        Gson gson = new Gson();
        AmazonOfferDto amazonOfferDto = AmazonOfferDto.builder()
                .asin("asin")
                .product_name("product name")
                .currentPrice(BigDecimal.valueOf(1))
                .locale("US")
                .targetPrice(BigDecimal.valueOf(2))
                .currency_symbol("$")
                .build();
        UserDto userDto = UserDto.builder()
                .name("name")
                .lastName("last name")
                .mail("test@test.com")
                .login("login")
                .password("password")
                .isActive(true)
                .build();
        User user = userMapper.mapToUser(userDto);
        User savedUser = userDao.save(user);
        AmazonOffer amazonOffer = amazonMapper.mapToAmazon(amazonOfferDto);
        String json = gson.toJson(amazonOfferDto);

        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/v1/amazon/updateOffer/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(json))

                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    public void getAllOffersTest()throws Exception{
        //Given
        Gson gson = new Gson();

        User user = User.builder()
                .name("name")
                .lastName("last name")
                .mail("test@test.com")
                .login("login")
                .password("password")
                .isActive(true)
                .build();
        User savedUser = userDao.save(user);
        AmazonOffer amazonOffer = AmazonOffer.builder()
                .asin("asin")
                .productName("product name")
                .currentPrice(BigDecimal.valueOf(1))
                .locale("US")
                .targetPrice(BigDecimal.valueOf(2))
                .currencySymbol("$")

                .build();
        //amazonOffer.getUserEntityList().add(savedUser);
        amazonDao.save(amazonOffer);


        //When & Then

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/amazon/getOffers/")
                                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].asin", Matchers.is("asin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].product_name", Matchers.is("product name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].current_price", Matchers.is(1.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].locale", Matchers.is("US")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].targetPrice", Matchers.is(2.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].currency_symbol", Matchers.is("$")));

    }
    @Test
    public void refreshPriceTest()throws Exception{
        //Given


        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/v1/amazon/refreshPrice")
                                .param("id","B005YQZ1KE")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    public void refreshPricesTest()throws Exception{
        //Given

        //When & Then

    }

    @Test
    public void getOfferTest()throws Exception{
        //Given
        AmazonOffer amazonOffer = AmazonOffer.builder()
                .asin("asin")
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
                                .get("/v1/amazon/getOffer/{id}",savedOffer.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }


}
