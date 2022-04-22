package com.parker.personalfinanceapp.models.reports;

import com.parker.personalfinanceapp.models.accounts.Loan;
import com.parker.personalfinanceapp.models.transactions.LoanPayment;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoansSummary implements Report {
    private List<Loan> loans;
    private List<LoanPayment> loanPayments;
}
