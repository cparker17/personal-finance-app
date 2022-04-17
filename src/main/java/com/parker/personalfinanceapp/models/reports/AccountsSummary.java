package com.parker.personalfinanceapp.models.reports;

import com.parker.personalfinanceapp.models.Report;
import com.parker.personalfinanceapp.models.accounts.BankAccount;
import com.parker.personalfinanceapp.models.accounts.LoanAccount;
import com.parker.personalfinanceapp.models.accounts.RetirementAccount;
import com.parker.personalfinanceapp.models.budget.Deposit;
import com.parker.personalfinanceapp.models.budget.Withdrawal;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountsSummary implements Report {
    private List<BankAccount> bankAccounts;
    private List<LoanAccount> loanAccounts;
    private List<RetirementAccount> retirementAccounts;
    private List<Deposit> deposits;
    private List<Withdrawal> withdrawals;
}
