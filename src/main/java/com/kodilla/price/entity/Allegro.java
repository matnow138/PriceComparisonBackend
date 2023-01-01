package com.kodilla.price.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Allegro {

    @Builder.Default
    private LocalDate addedDate = LocalDate.now();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String allegroID;
    private String productName;
    @NotNull
    private LocalDate expirationDate;

    @ManyToMany(
            mappedBy = "allegroList",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private List<User> userEntityList;


}
