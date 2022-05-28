package com.parker.personalfinanceapp.models;

import lombok.With;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    public abstract Long getId();
    public abstract Long getAccountNum();
    public abstract AccountType getAccountType();
    public abstract String getInstitutionName();
    public abstract BigDecimal getStartBalance();
    public abstract BigDecimal getCurrentBalance();
    public abstract void setAccountNum(Long accountNum);
    public abstract void setAccountType(AccountType accountType);
    public abstract void setInstitutionName(String name);
    public abstract void setStartBalance(BigDecimal amount);
    public abstract void setCurrentBalance(BigDecimal amount);
    public abstract void addTransaction(Transaction transaction);
    public abstract List<Deposit> getDeposits();
    public abstract List<Withdrawal> getWithdrawals();
    public abstract List<LoanPayment> getLoanPayments();
}
