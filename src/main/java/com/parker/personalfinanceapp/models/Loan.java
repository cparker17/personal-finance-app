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
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String institutionName;

    private Long accountNum;

    private Double interestRate;

    private BigDecimal startBalance;

    private BigDecimal currentBalance;

    private Integer monthlyDueDate;

    private BigDecimal paymentAmt;

    @OneToMany
    private List<Transaction> loanPayments;

    public void addTransaction(Transaction transaction) {
        if (loanPayments.isEmpty()) {
            loanPayments = new ArrayList<>();
        }
        loanPayments.add(transaction);
        currentBalance = currentBalance.subtract(transaction.getAmount());
    }
}
