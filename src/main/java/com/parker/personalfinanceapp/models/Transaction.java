package com.parker.personalfinanceapp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    public abstract void setAmount(BigDecimal amount);
    public abstract void setTransactionDate(LocalDate date);
    public abstract BigDecimal getAmount();
    public abstract LocalDate getTransactionDate();
}
