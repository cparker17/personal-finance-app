package com.parker.personalfinanceapp.models.reports;

import com.parker.personalfinanceapp.models.Report;
import com.parker.personalfinanceapp.models.budget.Category;
import com.parker.personalfinanceapp.models.budget.Expense;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseSummary implements Report {
    List<Category> categories;
    List<Expense> expenses;
}
