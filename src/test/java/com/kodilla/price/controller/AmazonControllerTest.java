package com.kodilla.price.controller;

import com.kodilla.price.AmazonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

@SpringJUnitWebConfig
@WebMvcTest(controllers = AmazonController.class)
public class AmazonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AmazonController amazonController;


}
