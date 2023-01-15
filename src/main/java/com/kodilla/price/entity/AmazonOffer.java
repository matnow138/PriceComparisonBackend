package com.kodilla.price.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmazonOffer {
    @Column(name ="AddedDate")
    @Builder.Default
    private LocalDate addedDate = LocalDate.now();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String asin;
    @Column(name ="ProductName")
    private String productName;
    @Column(name ="CurentPrice")
    private BigDecimal currentPrice;
    private String locale;
    @Column(name ="CurrencySymbol")
    private String currencySymbol;
    @Column(name ="TargetPrice")
    private BigDecimal targetPrice;

    @ManyToMany(
            mappedBy = "amazonOfferList",
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST
    )
    private List<User> userEntityList;
}

