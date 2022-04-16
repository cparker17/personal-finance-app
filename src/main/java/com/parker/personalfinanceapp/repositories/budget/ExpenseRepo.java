package com.parker.personalfinanceapp.repositories.budget;

import com.parker.personalfinanceapp.models.budget.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {
}
