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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private CategoryType categoryType;

    @Transient
    private String type;

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
