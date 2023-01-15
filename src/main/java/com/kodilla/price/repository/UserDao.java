package com.kodilla.price.repository;

import com.kodilla.price.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User, Long> {

    @Override
    User save(User user);

    Optional<User> findById(long id);

    void deleteById(long id);

    @Query(
            value = "SELECT * FROM user",
            nativeQuery = true)
    List<User> getAll();
}
