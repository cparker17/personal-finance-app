package com.parker.personalfinanceapp.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetirementPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer currentAge;

    private Integer retirementAge;

    private BigDecimal amountNeeded;

    private Boolean isOnTrack;

    private Integer additionalYearsToRetirement;
}
