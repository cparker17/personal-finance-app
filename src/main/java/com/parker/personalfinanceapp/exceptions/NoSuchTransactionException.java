package com.parker.personalfinanceapp.exceptions;

public class NoSuchTransactionException extends Exception {
    public NoSuchTransactionException(String message) {
        super(message);
    }
}
