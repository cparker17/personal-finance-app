package com.parker.personalfinanceapp.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Description required.")
    private String description;

    @NotBlank(message = "Name required.")
    private String name;

    private BigDecimal amountNeeded;

    private LocalDate dateAdded;

    @Transient
    private int monthsFromGoal;
}
