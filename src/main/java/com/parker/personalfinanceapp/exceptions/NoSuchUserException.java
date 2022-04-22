package com.parker.personalfinanceapp.exceptions;

public class NoSuchUserException extends PersonalFinanceAppException {
    public NoSuchUserException(String message) {
        super(message);
    }
}
