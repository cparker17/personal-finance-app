package com.parker.personalfinanceapp.exceptions;

import com.parker.personalfinanceapp.models.Loan;
import com.parker.personalfinanceapp.models.Report;
import com.parker.personalfinanceapp.models.LoanPayment;
import lombok.*;

import java.util.List;

public class PersonalFinanceAppException extends Exception {
    public PersonalFinanceAppException(String message) {
        super(message);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoansSummary implements Report {
        private List<Loan> loans;
        private List<LoanPayment> loanPayments;
    }
}
