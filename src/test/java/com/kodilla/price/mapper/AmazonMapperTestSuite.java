package com.kodilla.price.mapper;

import com.kodilla.price.domain.AmazonDto;
import com.kodilla.price.entity.Amazon;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AmazonMapperTestSuite {

    @Autowired
    private AmazonMapper amazonMapper;

    @Test
    public void mapToAmazonTest(){
        //Given
        AmazonDto amazonDto = AmazonDto.builder()
                .amazonID("1234")
                .productName("TestProduct")
                .expirationDate(LocalDate.of(2025, Month.DECEMBER, 25))
                .userEntityList(new ArrayList<>())
                .build();

        //When
        Amazon amazon = amazonMapper.mapToAmazon(amazonDto);

        //Then
        assertEquals(amazon.getAmazonID(),amazonDto.getAmazonID());

    }
    @Test
    public void mapToAmazonDtoTest(){
        //Given
        Amazon amazon = Amazon.builder()
                .amazonID("1234")
                .productName("TestProduct")
                .expirationDate(LocalDate.of(2025, Month.DECEMBER, 25))
                .userEntityList(new ArrayList<>())
                .build();

        //When
        AmazonDto amazonDto = amazonMapper.mapToAmazonDto(amazon);

        //Then
        assertEquals(amazon.getAmazonID(),amazonDto.getAmazonID());

    }
}
