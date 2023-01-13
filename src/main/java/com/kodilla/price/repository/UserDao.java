package com.kodilla.price.repository;

import com.kodilla.price.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudRepository<User, Long> {

    @Override
    User save(User user);

    Optional<User> findById(long id);

    void deleteById(long id);

    List<User> getAll();
}
