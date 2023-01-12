package com.kodilla.price.mapper;

import com.kodilla.price.domain.AmazonOfferDto;
import com.kodilla.price.entity.AmazonOffer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AmazonMapper {

    public AmazonOffer mapToAmazon(AmazonOfferDto amazonOfferDto){
        return AmazonOffer.builder()
                .asin(amazonOfferDto.getAsin())
                .productName(amazonOfferDto.getProduct_name())
                .addedDate(LocalDate.now())
                .currentPrice(amazonOfferDto.getCurrentPrice())
                .locale(amazonOfferDto.getLocale())
                .currencySymbol(amazonOfferDto.getCurrency_symbol())
                .targetPrice(amazonOfferDto.getTargetPrice())
                .build();
    }


}
