package com.parker.personalfinanceapp.models.accounts;

import com.parker.personalfinanceapp.models.enumerations.AccountType;
import com.parker.personalfinanceapp.models.transactions.Transaction;

import java.math.BigDecimal;

public interface Account {
    public Long getId();
    public Long getAccountNum();
    public AccountType getAccountType();
    public String getInstitutionName();
    public BigDecimal getStartBalance();
    public BigDecimal getCurrentBalance();
    public void setAccountNum(Long accountNum);
    public void setAccountType(AccountType accountType);
    public void setInstitutionName(String name);
    public void setStartBalance(BigDecimal amount);
    public void setCurrentBalance(BigDecimal amount);
    public void addTransaction(Transaction transaction);
}
