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
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;

    private String institutionName;

    private Long accountNum;

    private BigDecimal startBalance;

    private BigDecimal currentBalance;

    @OneToMany
    private List<Transaction> deposits = new ArrayList<>();

    @OneToMany
    private List<Transaction> withdrawals = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        if (transaction.getType().equals(TransactionType.DEPOSIT)) {
            deposits.add(transaction);
            currentBalance = currentBalance.add(transaction.getAmount());
        } else if (transaction.getType().equals(TransactionType.WITHDRAWAL)){
            withdrawals.add(transaction);
            currentBalance = currentBalance.subtract(transaction.getAmount());
        } else {
            currentBalance = currentBalance.subtract(transaction.getAmount());
        }
    }
}
