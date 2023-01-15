package com.kodilla.price.controller;

import com.google.gson.Gson;
import com.kodilla.price.UserController;
import com.kodilla.price.domain.AmazonOfferDto;
import com.kodilla.price.domain.UserDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(controllers = UserController.class)
public class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserController userController;


    @Test
    public void createUserTest() throws Exception {
        //Given
        Gson gson = new Gson();
        UserDto userDto=new UserDto("test name", "test lastName", "test@test.com", "login", "password",true);
        String json = gson.toJson(userDto);
        when(userController.createUser(any(UserDto.class))).thenReturn(ResponseEntity.ok(null));

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
        UserDto userDto=new UserDto("test name", "test lastName", "test@test.com", "login", "password",true);
        when(userController.findUser(anyLong())).thenReturn(ResponseEntity.ok().body(userDto));

        //When & then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/users/searchUser/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("test name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("test lastName")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail", Matchers.is("test@test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", Matchers.is("login")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("password")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active", Matchers.is(true)));
    }

    @Test
    public void updateUserTest() throws Exception{
        //Given
        Gson gson = new Gson();
        UserDto userDto=new UserDto("test name", "test lastName", "test@test.com", "login", "password",true);
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

        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/v1/users/blockUser/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    public void blockUserTest() throws Exception{
        //Given

        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/v1/users/blockUser/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void getUsersTest() throws Exception{
        //Given
        UserDto userDto1=new UserDto("test name1", "test lastName1", "test1@test.com", "login1", "password1",true);
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto1);
        when(userController.getUsers()).thenReturn(ResponseEntity.ok().body(userDtoList));
        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/users/getUsers")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("test name1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("test lastName1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mail", Matchers.is("test1@test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].login", Matchers.is("login1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password", Matchers.is("password1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].active", Matchers.is(true)));
    }

    @Test
    public void activateUserTest() throws Exception{
        //Given

        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/v1/users/activateUser/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void getOffersForUsersTest() throws Exception{
        //Given
        AmazonOfferDto amazonOfferDto = new AmazonOfferDto(1,"asin","product name", BigDecimal.valueOf(1), "US", "$",BigDecimal.valueOf(2));
        List<AmazonOfferDto> amazonOfferDtoList = new ArrayList<>();
        amazonOfferDtoList.add(amazonOfferDto);
        when(userController.getOffersForUser(anyLong())).thenReturn(ResponseEntity.ok().body(amazonOfferDtoList));
        //When & Then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/v1/users/getOffers/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].asin", Matchers.is("asin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].product_name", Matchers.is("product name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].current_price", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].locale", Matchers.is("US")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].currency_symbol", Matchers.is("$")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].targetPrice", Matchers.is(2)));
    }


}
