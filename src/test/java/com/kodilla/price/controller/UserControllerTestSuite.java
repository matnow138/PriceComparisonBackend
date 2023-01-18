package com.kodilla.price.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.kodilla.price.domain.UserDto;
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
    private AmazonDao amazonDao;


    @Test
    public void createUserTest() throws Exception {
        //Given
        Gson gson = new Gson();
        UserDto userDto = UserDto.builder()
                .name("createUser")
                .lastName("last name")
                .mail("test@test.com")
                .login("uniqueLogin")
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
                                .content(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("createUser")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("last name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail", Matchers.is("test@test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", Matchers.is("uniqueLogin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("password")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.status().is(200));
        User foundUser = userDao.findByLogin(userDto.getLogin()).orElse(null);
        userDao.deleteById(foundUser.getId());
    }

    @Test
    public void findUserTest() throws Exception {
        //Given
        User user = User.builder()
                .name("findUser")
                .lastName("last name")
                .mail("test@test.com")
                .login("login")
                .password("password")
                .isActive(true)
                .build();
        User savedUser = userDao.save(user);
        //When & then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/users/searchUser/{id}", savedUser.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("findUser")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("last name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail", Matchers.is("test@test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", Matchers.is("login")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("password")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active", Matchers.is(true)));
        userDao.deleteById(savedUser.getId());
    }


    @Test
    public void updateUserTest() throws Exception {
        //Given
        ObjectMapper objectMapper = new ObjectMapper();
        User user = User.builder()
                .name("updateUser")
                .lastName("last name")
                .mail("test@test.com")
                .login("uniqueLogin")
                .password("password")
                .isActive(true)
                .build();
        User savedUser = userDao.save(user);
        savedUser.setName("Unique Name");
        UserDto mappedUser = userMapper.mapToUserDto(savedUser);

        String json = objectMapper.writeValueAsString(mappedUser);


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
        userDao.deleteById(mappedUser.getId());
    }

    @Test
    public void deleteUserTest() throws Exception {
        //Given
        User user = User.builder()
                .name("deleteUser")
                .lastName("last name")
                .mail("test@test.com")
                .login("uniqueLogin")
                .password("password")
                .isActive(true)
                .build();
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
    public void blockUserTest() throws Exception {
        //Given
        User user = User.builder()
                .name("blockUser")
                .lastName("last name")
                .mail("test@test.com")
                .login("uniqueLogin")
                .password("password")
                .isActive(true)
                .build();
        User savedUser = userDao.save(user);
        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/v1/users/blockUser/{id}", savedUser.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is(200));
        userDao.deleteById(savedUser.getId());
    }

    @Test
    public void getUsersTest() throws Exception {
        //Given
        User user = User.builder()
                .name("getUsers")
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
                                .get("/v1/users/getUsers")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.notNullValue())));

        userDao.deleteById(savedUser.getId());
    }

    @Test
    public void activateUserTest() throws Exception {
        //Given
        User user = User.builder()
                .name("activateUser")
                .lastName("last name")
                .mail("test@test.com")
                .login("uniqueLogin")
                .password("password")
                .isActive(true)
                .build();
        User savedUser = userDao.save(user);
        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/v1/users/activateUser/{id}", savedUser.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is(200));
        userDao.deleteById(savedUser.getId());
    }

    @Test
    public void getOffersForUsersTest() throws Exception {
        //Given
        AmazonOffer amazonOffer = AmazonOffer.builder()
                .asin("asin")
                .productName("product name")
                .currentPrice(BigDecimal.valueOf(1))
                .locale("US")
                .targetPrice(BigDecimal.valueOf(2))
                .currencySymbol("$")
                .build();
        User user = User.builder()
                .name("getOffers")
                .lastName("last name")
                .mail("test@test.com")
                .login("uniqueLogin")
                .password("password")
                .isActive(true)
                .build();
        user.getAmazonOfferList().add(amazonOffer);
        User savedUser = userDao.save(user);
        AmazonOffer foundAmazon = savedUser.getAmazonOfferList().get(0);


        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/users/getOffers/{id}", savedUser.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].asin", Matchers.is("asin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].product_name", Matchers.is("product name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].current_price", Matchers.is(1.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].locale", Matchers.is("US")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].currency_symbol", Matchers.is("$")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].targetPrice", Matchers.is(2.00)));
        userDao.deleteById(savedUser.getId());
        amazonDao.deleteById(foundAmazon.getId());
    }


}
