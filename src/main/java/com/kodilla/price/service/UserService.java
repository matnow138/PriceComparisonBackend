package com.kodilla.price.service;

import com.kodilla.price.domain.AmazonOfferDto;
import com.kodilla.price.domain.UserDto;
import com.kodilla.price.entity.AmazonOffer;
import com.kodilla.price.entity.User;
import com.kodilla.price.mapper.AmazonMapper;
import com.kodilla.price.mapper.UserMapper;
import com.kodilla.price.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;

    private final AmazonMapper amazonMapper;

    public ResponseEntity<Void> createUser(UserDto userDto){
        User user = userMapper.mapToUser(userDto);
        userDao.save(user);
        return ResponseEntity.ok().build();
    }

    public UserDto findUserById(long id){
        User user = userDao.findById(id).orElse(null);
        try{
            return userMapper.mapToUserDto(user);
        }catch (Exception e){
            return null;
        }

    }

    public UserDto updateUser(UserDto userDto){
        User user = userMapper.mapToUser(userDto);
        userDao.save(user);
        return userMapper.mapToUserDto(user);
    }

    public void deleteUser(long id){
        userDao.deleteById(id);
    }

    public void blockUser(long id){
        User user = userDao.findById(id).orElse(null);
        user.setActive(false);
        userDao.save(user);
    }

    public void activateUser(long id){
        User user = userDao.findById(id).orElse(null);
        user.setActive(true);
        userDao.save(user);
    }

    public List<UserDto> getAll(){
        List<User> userList = userDao.getAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user:userList){
            userDtoList.add(userMapper.mapToUserDto(user));
        }
        return userDtoList;
    }

    public List<AmazonOfferDto> findOffersForUser(long id){
        User user = userDao.findById(id).orElse(null);
        List<AmazonOffer> amazonOfferList = user.getAmazonOfferList();
        List<AmazonOfferDto> amazonOfferDtoList = new ArrayList<>();
        for(AmazonOffer amazonOffer:amazonOfferList){
            amazonOfferDtoList.add(amazonMapper.mapToAmazonDto(amazonOffer));
        }
        return amazonOfferDtoList;
    }



}
