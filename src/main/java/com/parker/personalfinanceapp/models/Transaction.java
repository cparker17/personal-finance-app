package com.parker.personalfinanceapp.models;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long accountId;

    private BigDecimal amount;

    private LocalDate transactionDate;

    private TransactionType type;

    public Transaction(TransactionType transactionType) {
        this.type = transactionType;
    }
}
