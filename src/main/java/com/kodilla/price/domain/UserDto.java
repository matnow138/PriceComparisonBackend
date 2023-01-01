package com.kodilla.price.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private long id;
    private String name;
    private String lastName;
    private String mail;
    private String login;
    private String password;
    private boolean isActive;
}
