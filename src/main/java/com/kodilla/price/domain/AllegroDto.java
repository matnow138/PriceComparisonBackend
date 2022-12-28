package com.kodilla.price.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class AllegroDto {
    private long id;
    private String allegroID;
    private String productName;
    private LocalDate addedDate;
    private LocalDate expirationDate;
}
