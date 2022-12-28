package com.kodilla.price.dao;

import com.kodilla.price.entity.User;

import com.kodilla.price.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTestSuite {

    @Autowired
    private UserDao userDao;
    @Test
    public void saveTest(){
        //Given
        User user =User.builder()
                .name("John")
                .lastName("Smith")
                .mail("test@test.com")
                .login("testLogin")
                .password("testPassword")
                .build();
        //When
        User savedUser = userDao.save(user);

        //Then
        assertEquals(savedUser.getName(),user.getName());
    }

    @Test
    public void findUserByIdTest(){
        //Given
        User user =User.builder()
                .name("John")
                .lastName("Smith")
                .mail("test@test.com")
                .login("testLogin")
                .password("testPassword")
                .build();
        //When
        User savedUser = userDao.save(user);
        long userID = savedUser.getId();
        User foundUser = userDao.findById(userID).orElse(null);
        //Then
        assertEquals(savedUser.getName(), foundUser.getName());
    }

}
