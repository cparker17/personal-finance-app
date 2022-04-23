package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchBudgetException;
import com.parker.personalfinanceapp.models.Budget;
import com.parker.personalfinanceapp.models.BudgetFactory;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.repositories.BudgetRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BudgetService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    BudgetRepo budgetRepo;

    @Transactional
    public Budget createBudget(User user, Budget budget) {
        setUserBudget(user, budget);
        return budgetRepo.save(budget);
    }

    private void setUserBudget(User user, Budget budget) {
        user.setBudget(budget);
        userRepo.save(user);
    }

    @Transactional
    public Budget getBudget(User user) {
        return user.getBudget();
    }

    @Transactional
    public Budget updateBudget(User user, Budget budget) throws NoSuchBudgetException {
        setUserBudget(user, budget);
        budgetRepo.delete(BudgetFactory.getBudgetFromDB(budget.getId()));
        return budgetRepo.save(budget);
    }

    @Transactional
    public void deleteBudget(User user) throws NoSuchBudgetException {
        setUserBudget(user, null);
        budgetRepo.delete(BudgetFactory.getBudgetFromDB(user.getBudget().getId()));
    }
}
