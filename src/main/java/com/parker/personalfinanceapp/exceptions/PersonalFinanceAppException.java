package com.parker.personalfinanceapp.exceptions;

import com.parker.personalfinanceapp.models.LoanAccount;
import com.parker.personalfinanceapp.models.Report;
import com.parker.personalfinanceapp.models.LoanPayment;
import lombok.*;

import java.util.List;

public class PersonalFinanceAppException extends Exception {
    public PersonalFinanceAppException(String message) {
        super(message);
    }
}
