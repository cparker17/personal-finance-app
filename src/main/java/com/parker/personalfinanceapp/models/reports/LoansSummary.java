package com.parker.personalfinanceapp.models.reports;

import com.parker.personalfinanceapp.models.Report;
import com.parker.personalfinanceapp.models.accounts.LoanAccount;
import com.parker.personalfinanceapp.models.budget.LoanPayment;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoansSummary implements Report {
    private List<LoanAccount> loanAccounts;
    private List<LoanPayment> loanPayments;
}
