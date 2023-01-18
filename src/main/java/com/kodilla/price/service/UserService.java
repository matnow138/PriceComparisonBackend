package com.kodilla.price.service;

import com.kodilla.price.domain.AmazonOfferDto;
import com.kodilla.price.domain.UserDto;
import com.kodilla.price.entity.AmazonOffer;
import com.kodilla.price.entity.User;
import com.kodilla.price.exception.UserNotFoundException;
import com.kodilla.price.mapper.AmazonMapper;
import com.kodilla.price.mapper.UserMapper;
import com.kodilla.price.repository.UserDao;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;

    private final AmazonMapper amazonMapper;

    public ResponseEntity<Void> createUser(UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        userDao.save(user);
        return ResponseEntity.ok().build();
    }

    public UserDto findUserById(long id) throws UserNotFoundException {
        User user = userDao.findById(id).orElseThrow(UserNotFoundException::new);
        try {
            return userMapper.mapToUserDto(user);
        } catch (Exception e) {
            return null;
        }

    }

    public UserDto updateUser(UserDto userDto) throws UserNotFoundException {
        if (userDto.getId() != null) {
            User user = userDao.findById(userDto.getId()).orElseThrow(UserNotFoundException::new);
            user.setName(user.getName());
            user.setLastName(user.getLastName());
            user.setMail(user.getMail());
            user.setLogin(user.getLogin());
            user.setPassword(user.getPassword());
            user.setActive(user.isActive());
            userDao.save(user);
           return userMapper.mapToUserDto(user);
        } else {
            createUser(userDto);
            return userDto;
        }
    }

    public void deleteUser(long id) {
        userDao.deleteById(id);
    }

    public void blockUser(long id) throws UserNotFoundException {
        User user = userDao.findById(id).orElseThrow(UserNotFoundException::new);
        user.setActive(false);
        userDao.save(user);
    }

    public void activateUser(long id) throws UserNotFoundException {
        User user = userDao.findById(id).orElseThrow(UserNotFoundException::new);
        user.setActive(true);
        userDao.save(user);
    }

    public List<UserDto> getAll() {
        List<User> userList = userDao.getAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(userMapper.mapToUserDto(user));
        }
        return userDtoList;
    }

    public List<AmazonOfferDto> findOffersForUser(long id) throws UserNotFoundException{
        User user = userDao.findById(id).orElseThrow(UserNotFoundException::new);
        List<AmazonOffer> amazonOfferList = user.getAmazonOfferList();
        List<AmazonOfferDto> amazonOfferDtoList = new ArrayList<>();
        for (AmazonOffer amazonOffer : amazonOfferList) {
            amazonOfferDtoList.add(amazonMapper.mapToAmazonDto(amazonOffer));
        }
        return amazonOfferDtoList;
    }


}
