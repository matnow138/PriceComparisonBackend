package com.kodilla.price.mapper;

import com.kodilla.price.domain.AllegroDto;
import com.kodilla.price.entity.Allegro;
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
public class AllegroMapperTestSuite {

    @Autowired
    private AllegroMapper allegroMapper;

    @Test
    public void mapToAllegroTest(){
        //Given
        AllegroDto allegroDto = AllegroDto.builder()
                .allegroID("1234")
                .productName("TestProduct")
                .expirationDate(LocalDate.of(2025, Month.DECEMBER, 25))
                .userEntityList(new ArrayList<>())
                .build();

        //When
        Allegro allegro = allegroMapper.mapToAllegro(allegroDto);

        //Then
        assertEquals(allegroDto.getAllegroID(), allegro.getAllegroID());
    }
    @Test
    public void mapToAllegroDtoTest(){
        //Given
        Allegro allegro = Allegro.builder()
                .allegroID("1234")
                .productName("TestProduct")
                .expirationDate(LocalDate.of(2025, Month.DECEMBER, 25))
                .userEntityList(new ArrayList<>())
                .build();

        //When
        AllegroDto allegroDto = allegroMapper.mapToAllegroDto(allegro);

        //Then
        assertEquals(allegroDto.getAllegroID(), allegro.getAllegroID());
    }


}
