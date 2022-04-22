package com.parker.personalfinanceapp.models.transactions;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Transaction {
    public void setAmount(BigDecimal amount);
    public void setTransactionDate(LocalDate date);
    public BigDecimal getAmount();
    public LocalDate getTransactionDate();
}
