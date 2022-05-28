package com.parker.personalfinanceapp.models;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountsSummary implements Report {
    private List<Account> bankAccounts;
    private List<Account> loans;
    private List<Account> retirementAccounts;
    private List<Deposit> deposits;
    private List<Withdrawal> withdrawals;
}
