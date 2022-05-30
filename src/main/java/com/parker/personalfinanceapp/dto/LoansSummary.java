package com.parker.personalfinanceapp.dto;

import com.parker.personalfinanceapp.models.Loan;
import com.parker.personalfinanceapp.models.Transaction;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoansSummary implements Report {
    private List<Loan> loans;
    private List<Transaction> loanPayments;
}
