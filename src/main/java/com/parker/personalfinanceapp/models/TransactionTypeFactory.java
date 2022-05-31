package com.parker.personalfinanceapp.models;

public class TransactionTypeFactory {
    public static TransactionType createTransactionType(String type) {
        switch (type) {
            case "deposit": return TransactionType.DEPOSIT;
            case "expense": return TransactionType.EXPENSE;
            case "withdrawal": return TransactionType.WITHDRAWAL;
            default: return TransactionType.LOAN_PAYMENT;
        }
    }
}
