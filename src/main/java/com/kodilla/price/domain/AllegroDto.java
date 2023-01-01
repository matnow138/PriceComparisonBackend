package com.kodilla.price.domain;

import com.kodilla.price.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class AllegroDto {
    private long id;
    private String allegroID;
    private String productName;
    private LocalDate addedDate;
    private LocalDate expirationDate;
    private List<User> userEntityList;
}
