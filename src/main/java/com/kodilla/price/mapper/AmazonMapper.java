package com.kodilla.price.mapper;

import com.kodilla.price.domain.AmazonDto;
import com.kodilla.price.entity.Amazon;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AmazonMapper {

    public Amazon mapToAmazon(AmazonDto amazonDto){
        return Amazon.builder()
                .asin(amazonDto.getAsin())
                .productName(amazonDto.getProduct_name())
                .addedDate(LocalDate.now())
                .currentPrice(amazonDto.getCurrentPrice())
                .locale(amazonDto.getLocale())
                .currencySymbol(amazonDto.getCurrency_symbol())
                .build();
    }


}
