package com.kodilla.price.repository;

import com.kodilla.price.entity.AmazonOffer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AmazonDao extends CrudRepository<AmazonOffer, Long> {

    @Override
    AmazonOffer save(AmazonOffer amazonOffer);

    @Query(
            value = "SELECT * FROM amazonoffer",
            nativeQuery = true)
    List<AmazonOffer> getAll();

    Optional<AmazonOffer> findById(Long id);

    List<AmazonOffer> findAllByCurrencySymbol(String CurrencySymbol);

    Optional<AmazonOffer> findByAsin(String asin);

    @Override
    void deleteById(Long id);

    Optional<AmazonOffer> findByTargetPrice(BigDecimal bigDecimal);
}
