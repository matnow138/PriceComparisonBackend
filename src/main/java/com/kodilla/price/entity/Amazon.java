package com.kodilla.price.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Amazon {

    @Builder.Default
    private LocalDate addedDate = LocalDate.now();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String asin;
    private String product_name;
    private BigDecimal current_price;
    private String locale;
    private String currency_symbol;
    private double targetPrice;

    @ManyToMany(
            mappedBy = "amazonList",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private List<User> userEntityList;
}

