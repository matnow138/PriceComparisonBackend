package com.kodilla.price.mapper;

import com.kodilla.price.domain.UserDto;
import com.kodilla.price.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTestSuite {
    @Autowired
    private UserMapper userMapper;

  /*  @Test
    public void mapToUserDtoTest(){
        //Given
        User user =User.builder()
                .name("John")
                .lastName("Smith")
                .mail("test@test.com")
                .login("testLogin")
                .password("testPassword")
                .build();
        //When
        UserDto userDto = userMapper.mapToUserDto(user);

        //Then
        assertEquals(userDto.getName(),user.getName());
    }
    @Test
    public void mapToUserTest(){
        //Given
        UserDto userDto =UserDto.builder()
                .name("John")
                .lastName("Smith")
                .mail("test@test.com")
                .login("testLogin")
                .password("testPassword")
                .build();
        //When
        User user = userMapper.mapToUser(userDto);

        //Then
        assertEquals(user.getName(),userDto.getName());
    }*/
}
