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
public class LoanAccount extends Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private AccountType accountType;

    private String institutionName;

    private Long accountNum;

    private Double interestRate;

    private BigDecimal startBalance;

    private BigDecimal currentBalance;

    private Integer monthlyDueDate;

    private BigDecimal paymentAmt;

    @OneToMany
    private List<LoanPayment> loanPayments;

    public void addTransaction(Transaction transaction) {
        if (loanPayments.isEmpty()) {
            loanPayments = new ArrayList<>();
        } else {
            loanPayments.add((LoanPayment) transaction);
        }
    }

    @Override
    public List<Deposit> getDeposits() {
        return null;
    }

    @Override
    public List<Withdrawal> getWithdrawals() {
        return null;
    }
}
