package com.parker.personalfinanceapp.dto;

import com.parker.personalfinanceapp.models.Category;
import com.parker.personalfinanceapp.models.Transaction;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseSummary implements Report {
    List<Category> categories;
    List<Transaction> expenses;
}
