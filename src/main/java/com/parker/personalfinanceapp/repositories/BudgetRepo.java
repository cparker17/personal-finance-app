package com.parker.personalfinanceapp.repositories;

import com.parker.personalfinanceapp.models.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepo extends JpaRepository<Budget, Long> {
}
