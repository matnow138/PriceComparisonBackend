package com.kodilla.price.repository;

import com.kodilla.price.entity.Allegro;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AllegroDao extends CrudRepository<Allegro, Long> {

    @Override
    Allegro save(Allegro allegro);

    Optional<Allegro> findById(long id);

    void deleteById(long id);
}
