package com.kodilla.price.controller;

import com.google.gson.Gson;
import com.kodilla.price.UserController;
import com.kodilla.price.domain.AmazonOfferDto;
import com.kodilla.price.domain.UserDto;
import com.kodilla.price.entity.AmazonOffer;
import com.kodilla.price.entity.User;
import com.kodilla.price.mapper.AmazonMapper;
import com.kodilla.price.mapper.UserMapper;
import com.kodilla.price.repository.AmazonDao;
import com.kodilla.price.repository.UserDao;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

//@SpringJUnitWebConfig
//@WebMvcTest(controllers = UserController.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AmazonMapper amazonMapper;
    @Autowired
    private AmazonDao amazonDao;

    @AfterEach
    public void CleanUp(){
        userDao.deleteAll();
    }

    @Test
    public void createUserTest() throws Exception {
        //Given
        Gson gson = new Gson();
        UserDto userDto = UserDto.builder()
                .name("name")
                .lastName("last name")
                .mail("test@test.com")
                .login("login")
                .password("password")
                .isActive(true)
                .build();
        String json = gson.toJson(userDto);

        //When & Then

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(json)
                                )

                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    public void findUserTest() throws Exception{
        //Given
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
        //When & then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/users/searchUser/{id}", savedUser.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("last name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail", Matchers.is("test@test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", Matchers.is("login")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("password")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active", Matchers.is(true)));
    }

    @Test
    public void updateUserTest() throws Exception{
        //Given
        Gson gson = new Gson();
        UserDto userDto = UserDto.builder()
                .name("name")
                .lastName("last name")
                .mail("test@test.com")
                .login("login")
                .password("password")
                .isActive(true)
                .build();
        String json = gson.toJson(userDto);
        //When & Then

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(json)
                )

                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    public void deleteUserTest() throws Exception{
        //Given
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

        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/v1/users/deleteUser/{id}", savedUser.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    public void blockUserTest() throws Exception{
        //Given
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
        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/v1/users/blockUser/{id}", savedUser.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    public void getUsersTest() throws Exception{
        //Given
        UserDto userDto = UserDto.builder()
                .name("name")
                .lastName("last name")
                .mail("test@test.com")
                .login("login")
                .password("password")
                .isActive(true)
                .build();
        User user = userMapper.mapToUser(userDto);
        userDao.save(user);


        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/users/getUsers")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("last name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mail", Matchers.is("test@test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].login", Matchers.is("login")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password", Matchers.is("password")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].active", Matchers.is(true)));
    }

    @Test
    public void activateUserTest() throws Exception{
        //Given
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
        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/v1/users/activateUser/{id}", savedUser.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

   /* @Test
    public void getOffersForUsersTest() throws Exception{
        //Given
        AmazonOfferDto amazonOfferDto = AmazonOfferDto.builder()
                .asin("asin")
                .product_name("product name")
                .currentPrice(BigDecimal.valueOf(1))
                .locale("US")
                .targetPrice(BigDecimal.valueOf(2))
                .currency_symbol("$")
                .build();
        AmazonOffer amazonOffer = amazonMapper.mapToAmazon(amazonOfferDto);
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
        User foundUser = userDao.findById(savedUser.getId()).orElse(null);
        foundUser.getAmazonOfferList().add(amazonOffer);
        userDao.save(foundUser);


      //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/users/getOffers/{id}", foundUser.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].asin", Matchers.is("asin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].product_name", Matchers.is("product name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].current_price", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].locale", Matchers.is("US")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].currency_symbol", Matchers.is("$")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].targetPrice", Matchers.is(2)));
    }*/


}
