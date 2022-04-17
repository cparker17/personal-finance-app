package com.parker.personalfinanceapp.services.budget;

import com.parker.personalfinanceapp.exceptions.NoSuchBudgetException;
import com.parker.personalfinanceapp.models.budget.Budget;
import com.parker.personalfinanceapp.repositories.budget.BudgetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class BudgetFactory {
    @Autowired
    static BudgetRepo budgetRepo;

    public static Budget getBudget(Long id) throws NoSuchBudgetException {
        Optional<Budget> budgetOptional = budgetRepo.findById(id);
        if (budgetOptional.isPresent()) {
            return budgetOptional.get();
        } else {
            throw new NoSuchBudgetException("Budget does not exist.");
        }
    }
}
