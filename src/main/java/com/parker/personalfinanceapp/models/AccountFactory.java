package com.parker.personalfinanceapp.models;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;

public class AccountFactory {
    public static Account createAccount(String accountType) throws NoSuchAccountException {
        switch (accountType) {
            case "bank": return new BankAccount();
            case "retirement": return new RetirementAccount();
            default: throw new NoSuchAccountException("Account type does not exist.");
        }
    }
}
