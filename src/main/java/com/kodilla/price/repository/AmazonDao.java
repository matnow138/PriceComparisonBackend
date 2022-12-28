package com.kodilla.price.repository;

import com.kodilla.price.entity.Amazon;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AmazonDao extends CrudRepository<Amazon, Long> {

    @Override
    Amazon save(Amazon amazon);

    Optional<Amazon> findById(long id);
}
