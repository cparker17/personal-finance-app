package com.parker.personalfinanceapp.models.accounts;

import com.parker.personalfinanceapp.models.enumerations.AccountType;
import com.parker.personalfinanceapp.models.transactions.Deposit;
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
public class RetirementAccount implements Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String institutionName;

    private Long accountNum;

    private AccountType accountType;

    private BigDecimal startBalance;

    private BigDecimal currentBalance;

    @OneToMany
    private List<Deposit> deposits;

    public void addTransaction(Transaction transaction) {
        if (deposits.isEmpty()) {
            deposits = new ArrayList<>();
        } else {
            deposits.add((Deposit) transaction);
        }
    }
}
