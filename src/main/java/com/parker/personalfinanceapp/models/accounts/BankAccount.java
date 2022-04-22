package com.parker.personalfinanceapp.models.accounts;

import com.parker.personalfinanceapp.models.enumerations.AccountType;
import com.parker.personalfinanceapp.models.transactions.Deposit;
import com.parker.personalfinanceapp.models.transactions.Transaction;
import com.parker.personalfinanceapp.models.transactions.Withdrawal;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount implements Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private AccountType accountType;

    private String institutionName;

    private Long accountNum;

    private BigDecimal startBalance;

    private BigDecimal currentBalance;

    @OneToMany
    private List<Deposit> deposits = new ArrayList<>();

    @OneToMany
    private List<Withdrawal> withdrawals;

    public void addTransaction(Transaction transaction) {
        if (transaction instanceof Deposit) {
            deposits.add((Deposit) transaction);
        } else {
            withdrawals.add((Withdrawal) transaction);
        }
    }
}
