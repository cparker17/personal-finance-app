package com.parker.personalfinanceapp.dto;

import com.parker.personalfinanceapp.models.Budget;
import com.parker.personalfinanceapp.models.Transaction;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetActualReport implements Report {
    private Budget budget;
    private List<Transaction> expenses;
}
