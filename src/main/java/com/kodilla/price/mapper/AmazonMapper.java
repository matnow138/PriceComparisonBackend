package com.kodilla.price.mapper;

import com.kodilla.price.domain.AmazonOfferDto;
import com.kodilla.price.entity.AmazonOffer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AmazonMapper {

    public AmazonOffer mapToAmazon(AmazonOfferDto amazonOfferDto) {
        if(amazonOfferDto.getId()!=null) {
            return AmazonOffer.builder()
                    .id(amazonOfferDto.getId())
                    .asin(amazonOfferDto.getAsin())
                    .productName(amazonOfferDto.getProduct_name())
                    .addedDate(LocalDate.now())
                    .currentPrice(amazonOfferDto.getCurrentPrice())
                    .locale(amazonOfferDto.getLocale())
                    .currencySymbol(amazonOfferDto.getCurrency_symbol())
                    .targetPrice(amazonOfferDto.getTargetPrice())
                    .build();
        }else{
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

    public AmazonOfferDto mapToAmazonDto(AmazonOffer amazonOffer) {
        return AmazonOfferDto.builder()
                .id(amazonOffer.getId())
                .asin(amazonOffer.getAsin())
                .product_name(amazonOffer.getProductName())
                .currentPrice(amazonOffer.getCurrentPrice())
                .locale(amazonOffer.getLocale())
                .currency_symbol(amazonOffer.getCurrencySymbol())
                .targetPrice(amazonOffer.getTargetPrice())
                .build();
    }


}
