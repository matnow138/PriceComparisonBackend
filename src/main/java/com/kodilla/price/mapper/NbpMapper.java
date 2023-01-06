package com.kodilla.price.mapper;

import com.kodilla.price.domain.NbpDto;
import com.kodilla.price.entity.Nbp;
import org.springframework.stereotype.Service;

@Service
public class NbpMapper {

    public Nbp mapToNbp(NbpDto nbpDto){
        return Nbp.builder()
                .currency(nbpDto.getCurrency())
                .exchangeRate(nbpDto.getRates().get(0).getAsk())
                .addedDate(nbpDto.getAddedDate())
                .build();
    }


}
