package com.parker.personalfinanceapp.services.accounts;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.models.accounts.Account;
import com.parker.personalfinanceapp.models.accounts.BankAccount;
import com.parker.personalfinanceapp.models.accounts.LoanAccount;
import com.parker.personalfinanceapp.models.accounts.RetirementAccount;

public class AccountFactory {
    public static Account createAccount(String accountType) throws NoSuchAccountException {
        switch (accountType) {
            case "BankAccount":
                return new BankAccount();
            case "LoanAcount":
                return new LoanAccount();
            case "RetirementAccount":
                return new RetirementAccount();
            default:
                throw new NoSuchAccountException("Account type does not exist.");
        }
    }
}
