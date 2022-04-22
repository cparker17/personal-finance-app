package com.parker.personalfinanceapp.models.accounts;

import com.parker.personalfinanceapp.models.enumerations.AccountType;
import com.parker.personalfinanceapp.models.transactions.LoanPayment;
import com.parker.personalfinanceapp.models.transactions.Transaction;
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

    private AccountType accountType;

    private String lenderName;

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
}
