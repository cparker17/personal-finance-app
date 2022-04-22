package com.parker.personalfinanceapp.exceptions;

public class NoSuchAccountException extends PersonalFinanceAppException {
    public NoSuchAccountException(String message) {
        super(message);
    }
}
