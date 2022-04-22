package com.parker.personalfinanceapp.models.reports;

import com.parker.personalfinanceapp.models.budget.Budget;
import com.parker.personalfinanceapp.models.transactions.Expense;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetActualReport implements Report {
    private Budget budget;
    private List<Expense> expenses;
}
