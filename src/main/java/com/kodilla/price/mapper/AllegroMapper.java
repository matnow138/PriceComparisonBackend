package com.kodilla.price.mapper;

import com.kodilla.price.domain.AllegroDto;
import com.kodilla.price.entity.Allegro;
import org.springframework.stereotype.Service;

@Service
public class AllegroMapper {

    public Allegro mapToAllegro(AllegroDto allegroDto){
        return Allegro.builder()
                .allegroID(allegroDto.getAllegroID())
                .productName(allegroDto.getProductName())
                .expirationDate(allegroDto.getExpirationDate())
                .addedDate(allegroDto.getAddedDate())
                .userEntityList(allegroDto.getUserEntityList())
                .build();
    }
    public AllegroDto mapToAllegroDto(Allegro allegro){
        return AllegroDto.builder()
                .allegroID(allegro.getAllegroID())
                .productName(allegro.getProductName())
                .expirationDate(allegro.getExpirationDate())
                .addedDate(allegro.getAddedDate())
                .userEntityList(allegro.getUserEntityList())
                .build();
    }
}
