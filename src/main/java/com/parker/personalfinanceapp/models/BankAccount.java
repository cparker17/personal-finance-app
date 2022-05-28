package com.parker.personalfinanceapp.models;

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
public class BankAccount extends Account {
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
            currentBalance = currentBalance.add(transaction.getAmount());
        } else if (transaction instanceof Withdrawal){
            withdrawals.add((Withdrawal) transaction);
            currentBalance = currentBalance.subtract(transaction.getAmount());
        } else {
            currentBalance = currentBalance.subtract(transaction.getAmount());
        }
    }
}
