package com.parker.personalfinanceapp.models.budget;

import com.parker.personalfinanceapp.models.enumerations.CategoryType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private CategoryType categoryType;

    private BigDecimal monthlyBudgetAmt;

    @OneToMany
    private List<Deposit> deposits;

    @OneToMany
    private List<Expense> expenses;

    @OneToMany
    private List<LoanPayment> loanPayments;

    @OneToMany
    private List<Withdrawal> withdrawals;
}
