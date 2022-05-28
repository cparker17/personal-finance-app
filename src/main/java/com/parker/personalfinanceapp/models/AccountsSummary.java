package com.parker.personalfinanceapp.models;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountsSummary implements Report {
    private List<BankAccount> bankAccounts;
    private List<LoanAccount> loans;
    private List<RetirementAccount> retirementAccounts;
    private List<Deposit> deposits;
    private List<Withdrawal> withdrawals;
}
