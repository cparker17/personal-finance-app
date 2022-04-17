package com.parker.personalfinanceapp.models.reports;

import com.parker.personalfinanceapp.models.Report;
import com.parker.personalfinanceapp.models.budget.Budget;
import com.parker.personalfinanceapp.models.budget.Expense;

import java.util.List;

public class BudgetActualReport implements Report {
    private Budget budget;
    private List<Expense> expenses;
}
