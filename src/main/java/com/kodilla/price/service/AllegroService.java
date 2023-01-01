package com.kodilla.price.service;

import com.kodilla.price.domain.AllegroDto;
import com.kodilla.price.entity.Allegro;
import com.kodilla.price.mapper.AllegroMapper;
import com.kodilla.price.repository.AllegroDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AllegroService {

    private final AllegroDao allegroDao;
    private final AllegroMapper allegroMapper;

    public void createAllegroOffer(AllegroDto allegroDto){
        Allegro allegro = allegroMapper.mapToAllegro(allegroDto);
        allegroDao.save(allegro);

    }
}
