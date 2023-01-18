package com.kodilla.price.mapper;

import com.kodilla.price.domain.UserDto;
import com.kodilla.price.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User mapToUser(UserDto userDto) {
        if (userDto.getId() != null)
            return User.builder()
                    .id(userDto.getId())
                    .name(userDto.getName())
                    .lastName(userDto.getLastName())
                    .mail(userDto.getMail())
                    .login(userDto.getLogin())
                    .password(userDto.getPassword())
                    .isActive(userDto.isActive())
                    .build();
        else {
            return User.builder()
                    .name(userDto.getName())
                    .lastName(userDto.getLastName())
                    .mail(userDto.getMail())
                    .login(userDto.getLogin())
                    .password(userDto.getPassword())
                    .isActive(userDto.isActive())
                    .build();
        }
    }

    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .mail(user.getMail())
                .login(user.getLogin())
                .password(user.getPassword())
                .isActive(user.isActive())
                .build();

    }
}
