package com.kodilla.price.dao;

import com.kodilla.price.entity.Allegro;
import com.kodilla.price.entity.User;
import com.kodilla.price.repository.AllegroDao;
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

@SpringBootTest
@RunWith(SpringRunner.class)
public class AllegroTestSuite {

    @Autowired
    private AllegroDao allegroDao;

    @Autowired
    private UserDao userDao;


    @Test
    public void saveOfferTest() {
        //Given
        User user = User.builder()
                .name("John")
                .lastName("Smith")
                .mail("test@test.com")
                .login("testLogin")
                .password("testPassword")
                .build();
        Allegro allegro = Allegro.builder()
                .allegroID("1234")
                .productName("TestProduct")
                .expirationDate(LocalDate.of(2025, Month.DECEMBER, 25))
                .userEntityList(new ArrayList<>())
                .build();
        allegro.getUserEntityList().add(user);

        //When
        Allegro savedAllegro = allegroDao.save(allegro);
        Long allegroId = savedAllegro.getId();
        //Then
        assertEquals(savedAllegro.getAllegroID(), allegro.getAllegroID());
        assertEquals(savedAllegro.getUserEntityList().get(0).getLogin(), allegro.getUserEntityList().get(0).getLogin());
        allegroDao.deleteById(allegroId);
        userDao.deleteById(savedAllegro.getUserEntityList().get(0).getId());
        assertFalse(allegroDao.findById(savedAllegro.getId()).isPresent());
        assertFalse(userDao.findById(savedAllegro.getUserEntityList().get(0).getId()).isPresent());
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
        Allegro allegro = Allegro.builder()
                .allegroID("1234")
                .productName("TestProduct")
                .expirationDate(LocalDate.of(2025, Month.DECEMBER, 25))
                .userEntityList(new ArrayList<>())
                .build();
        allegro.getUserEntityList().add(user);
        user.getAllegroList().add(allegro);
        //When
        Allegro savedAllegro = allegroDao.save(allegro);
        long allegroId = savedAllegro.getId();
        Allegro foundAllegro = allegroDao.findById(allegroId).orElse(null);

        //Then
        assertEquals(foundAllegro.getAllegroID(), allegro.getAllegroID());
    }
}
