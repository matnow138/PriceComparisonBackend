package com.kodilla.price.dao;

import com.kodilla.price.entity.Amazon;
import com.kodilla.price.entity.User;
import com.kodilla.price.repository.AmazonDao;
import com.kodilla.price.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AmazonTestSuite {

    @Autowired
    private AmazonDao amazonDao;
    @Autowired
    private UserDao userDao;

   /* @Test
    public void saveOfferTest() {
        //Given
        User user = User.builder()
                .name("John")
                .lastName("Smith")
                .mail("test@test.com")
                .login("testLogin")
                .password("testPassword")
                .build();
        Amazon amazon = Amazon.builder()
                .asin("1234")
                .productName("TestProduct")
                .userEntityList(new ArrayList<>())
                .build();
        amazon.getUserEntityList().add(user);

        //When
        Amazon savedAmazon = amazonDao.save(amazon);

        //Then
        assertEquals(savedAmazon.getAsin(), amazon.getAsin());
        assertEquals(savedAmazon.getUserEntityList().get(0).getLogin(), amazon.getUserEntityList().get(0).getLogin());
        amazonDao.deleteById(savedAmazon.getId());
        userDao.deleteById(savedAmazon.getUserEntityList().get(0).getId());
        assertFalse(amazonDao.findById(savedAmazon.getId()).isPresent());
        assertFalse(userDao.findById(savedAmazon.getUserEntityList().get(0).getId()).isPresent());
    }

    @Test
    public void findOfferByIdTest() {
        //Given
        User user = User.builder()
                .name("John")
                .lastName("Smith")
                .mail("test@test.com")
                .login("testLogin")
                .password("testPassword")
                .build();
        Amazon amazon = Amazon.builder()
                .asin("1234")
                .productName("TestProduct")
                .userEntityList(new ArrayList<>())
                .build();
        amazon.getUserEntityList().add(user);

        //When
        Amazon savedAmazon = amazonDao.save(amazon);
        Amazon foundAmazon = amazonDao.findById(savedAmazon.getId()).orElse(null);

        //Then
        assertEquals(foundAmazon.getAsin(), amazon.getAsin());
        amazonDao.deleteById(savedAmazon.getId());
        userDao.deleteById(savedAmazon.getUserEntityList().get(0).getId());
        assertFalse(amazonDao.findById(savedAmazon.getId()).isPresent());
        assertFalse(userDao.findById(savedAmazon.getUserEntityList().get(0).getId()).isPresent());


    }*/


}
