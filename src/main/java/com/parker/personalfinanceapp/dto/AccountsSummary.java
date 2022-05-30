package com.parker.personalfinanceapp.dto;

import com.parker.personalfinanceapp.models.Account;
import com.parker.personalfinanceapp.models.Transaction;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountsSummary implements Report {
    private List<Account> accounts;
    private List<Transaction> deposits;
    private List<Transaction> withdrawals;
}
