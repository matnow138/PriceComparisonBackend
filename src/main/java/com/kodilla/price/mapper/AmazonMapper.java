package com.kodilla.price.mapper;

import com.kodilla.price.domain.AmazonDto;
import com.kodilla.price.entity.Amazon;
import org.springframework.stereotype.Service;

@Service
public class AmazonMapper {

    public Amazon mapToAmazon(AmazonDto amazonDto){
        return Amazon.builder()
                .amazonID(amazonDto.getAmazonID())
                .productName(amazonDto.getProductName())
                .addedDate(amazonDto.getAddedDate())
                .expirationDate(amazonDto.getExpirationDate())
                .build();
    }

    public AmazonDto mapToAmazonDto(Amazon amazon){
        return AmazonDto.builder()
                .amazonID(amazon.getAmazonID())
                .productName(amazon.getProductName())
                .addedDate(amazon.getAddedDate())
                .expirationDate(amazon.getExpirationDate())
                .build();
    }
}
