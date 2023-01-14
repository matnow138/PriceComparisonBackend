package com.kodilla.price.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatesDto {

    private String no;
    private String effectiveDate;
    private BigDecimal bid;
    private BigDecimal ask;
}
