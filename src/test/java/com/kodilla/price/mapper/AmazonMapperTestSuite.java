package com.kodilla.price.mapper;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AmazonMapperTestSuite {

    @Autowired
    private AmazonMapper amazonMapper;

   /* @Test
    public void mapToAmazonTest(){
        //Given
        AmazonOfferDto amazonDto = AmazonOfferDto.builder()
                .asin("1234")
                .productName("TestProduct")
                .expirationDate(LocalDate.of(2025, Month.DECEMBER, 25))
                .userEntityList(new ArrayList<>())
                .build();

        //When
        AmazonOffer amazon = amazonMapper.mapToAmazon(amazonDto);

        //Then
        assertEquals(amazon.getAmazonID(),amazonDto.getAmazonID());

    }
    @Test
    public void mapToAmazonDtoTest(){
        //Given
        AmazonOffer amazon = AmazonOffer.builder()
                .amazonID("1234")
                .productName("TestProduct")
                .expirationDate(LocalDate.of(2025, Month.DECEMBER, 25))
                .userEntityList(new ArrayList<>())
                .build();

        //When
        AmazonOfferDto amazonDto = amazonMapper.mapToAmazonDto(amazon);

        //Then
        assertEquals(amazon.getAmazonID(),amazonDto.getAmazonID());

    }*/
}
