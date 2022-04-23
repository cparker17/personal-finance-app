package com.parker.personalfinanceapp.models;

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
