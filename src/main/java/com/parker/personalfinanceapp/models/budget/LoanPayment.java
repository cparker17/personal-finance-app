package com.parker.personalfinanceapp.models.budget;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanPayment implements Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long accountId;

    private BigDecimal amount;

    private LocalDate transactionDate;
}
