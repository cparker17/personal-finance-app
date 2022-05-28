package com.parker.personalfinanceapp.models;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoansSummary implements Report {
    private List<Account> loans;
    private List<LoanPayment> loanPayments;
}
