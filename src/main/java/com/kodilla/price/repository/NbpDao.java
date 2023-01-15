package com.kodilla.price.repository;

import com.kodilla.price.entity.Nbp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface NbpDao extends CrudRepository<Nbp, Long> {

    @Override
    Nbp save(Nbp nbp);

    Optional<Nbp> findById(long id);

    Optional<Nbp> findByCurrency(String currency);
}
