package com.parker.personalfinanceapp.models;

import lombok.*;

import javax.persistence.*;
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

    @Transient
    private String stringType;

    public Transaction(TransactionType transactionType) {
        this.type = transactionType;
    }
}
