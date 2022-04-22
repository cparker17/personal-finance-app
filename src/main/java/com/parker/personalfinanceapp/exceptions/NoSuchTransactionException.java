package com.parker.personalfinanceapp.exceptions;

public class NoSuchTransactionException extends PersonalFinanceAppException {
    public NoSuchTransactionException(String message) {
        super(message);
    }
}
