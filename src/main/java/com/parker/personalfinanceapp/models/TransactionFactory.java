package com.parker.personalfinanceapp.models;

import com.parker.personalfinanceapp.exceptions.NoSuchTransactionException;

public class TransactionFactory {
    public static Transaction createTransaction(String transactionType) throws NoSuchTransactionException {
        switch(transactionType) {
            case "deposit": return new Deposit();
            case "withdrawal": return new Withdrawal();
            case "expense": return new Expense();
            case "loanPayment": return new LoanPayment();
            default: throw new NoSuchTransactionException("Transaction type does not exist.");
        }
    }
}
