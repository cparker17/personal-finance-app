package com.parker.personalfinanceapp.models.budget;

import com.parker.personalfinanceapp.exceptions.NoSuchBudgetException;
import com.parker.personalfinanceapp.repositories.BudgetRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class BudgetFactory {
    @Autowired
    static BudgetRepo budgetRepo;

    public static Budget getBudgetFromDB(Long id) throws NoSuchBudgetException {
        Optional<Budget> budgetOptional = budgetRepo.findById(id);
        if (budgetOptional.isPresent()) {
            return budgetOptional.get();
        } else {
            throw new NoSuchBudgetException("Budget does not exist.");
        }
    }
}
