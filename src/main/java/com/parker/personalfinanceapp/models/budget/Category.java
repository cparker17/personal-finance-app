package com.parker.personalfinanceapp.models.budget;

import com.parker.personalfinanceapp.models.enumerations.CategoryType;
import com.parker.personalfinanceapp.models.transactions.Expense;
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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private CategoryType categoryType;

    private BigDecimal monthlyBudgetAmt;

    @OneToMany
    private List<Expense> expenses;

    public void addExpense(Expense expense) {
        if (expenses.isEmpty()) {
            expenses = new ArrayList<>();
        } else {
            expenses.add(expense);
        }
    }
}
