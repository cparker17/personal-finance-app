package com.parker.personalfinanceapp.models;

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
