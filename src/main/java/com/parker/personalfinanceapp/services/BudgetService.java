package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchBudgetException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.budget.Budget;
import com.parker.personalfinanceapp.models.budget.BudgetFactory;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
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
    public Budget createBudget(Long userId, Budget budget) throws NoSuchUserException {
        User user = UserFactory.getUser(userId);
        user.setBudget(budget);
        userRepo.save(user);
        return budgetRepo.save(budget);
    }

    @Transactional
    public Budget getBudget(Long userId) throws NoSuchUserException {
        return UserFactory.getUser(userId).getBudget();
    }

    @Transactional
    public Budget updateBudget(Long userId, Budget budget) throws NoSuchUserException, NoSuchBudgetException {
        User user = UserFactory.getUser(userId);
        user.setBudget(budget);
        userRepo.save(user);
        budgetRepo.delete(BudgetFactory.getBudgetFromDB(budget.getId()));
        return budgetRepo.save(budget);
    }

    @Transactional
    public void deleteBudget(Long userId) throws NoSuchBudgetException, NoSuchUserException {
        User user = UserFactory.getUser(userId);
        user.setBudget(null);
        userRepo.save(user);
        budgetRepo.delete(BudgetFactory.getBudgetFromDB(user.getBudget().getId()));
    }


}
