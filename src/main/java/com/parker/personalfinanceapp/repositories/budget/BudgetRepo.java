package com.parker.personalfinanceapp.repositories.budget;

import com.parker.personalfinanceapp.models.budget.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepo extends JpaRepository<Budget, Long> {
}
