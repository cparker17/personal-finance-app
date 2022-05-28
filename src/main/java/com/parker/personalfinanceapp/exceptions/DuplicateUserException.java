package com.parker.personalfinanceapp.exceptions;

public class DuplicateUserException extends PersonalFinanceAppException{
    public DuplicateUserException(String message) {
        super(message);
    }
}
