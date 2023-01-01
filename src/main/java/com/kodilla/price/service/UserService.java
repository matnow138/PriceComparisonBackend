package com.kodilla.price.service;

import com.kodilla.price.domain.UserDto;
import com.kodilla.price.entity.User;
import com.kodilla.price.mapper.UserMapper;
import com.kodilla.price.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;

    public void createUser(UserDto userDto){
        User user = userMapper.mapToUser(userDto);
        userDao.save(user);
    }

    public UserDto findUserById(long id){
        User user = userDao.findById(id).orElse(null);
        return userMapper.mapToUserDto(user);
    }

    public UserDto updateUser(UserDto userDto){
        User user = userMapper.mapToUser(userDto);
        userDao.save(user);
        return userMapper.mapToUserDto(user);
    }

}
