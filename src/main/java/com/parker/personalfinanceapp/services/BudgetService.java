package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchBudgetException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.*;
import com.parker.personalfinanceapp.repositories.BudgetRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Budget getBudget(User user) throws NoSuchBudgetException, NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(user.getId());
        if (userOptional.isPresent()) {
            return userOptional.get().getBudget();
        } else {
            throw new NoSuchUserException("User not found");
        }
    }

    @Transactional
    public Budget updateBudget(User user, Budget budget) throws NoSuchBudgetException, NoSuchUserException {
        setUserBudget(user, budget);
        budgetRepo.delete(getBudget(user));
        return budgetRepo.save(budget);
    }

    @Transactional
    public void deleteBudget(User user) throws NoSuchBudgetException, NoSuchUserException {
        setUserBudget(user, null);
        budgetRepo.delete(getBudget(user));
    }

    public List<Category> getNeedsCategories(Budget budget) {
        List<Category> needsCategories = new ArrayList<>();
        for (Category category : budget.getCategories()) {
            if (category.getCategoryType().equals(CategoryType.NEEDS)) {
                needsCategories.add(category);
            }
        }
        return needsCategories;
    }

    public List<Category> getWantsCategories(Budget budget) {
        List<Category> wantsCategories = new ArrayList<>();
        for (Category category : budget.getCategories()) {
            if (category.getCategoryType().equals(CategoryType.WANTS)) {
                wantsCategories.add(category);
            }
        }
        return wantsCategories;
    }

    public List<Category> getSavingsCategories(Budget budget) {
        List<Category> savingsCategories = new ArrayList<>();
        for (Category category : budget.getCategories()) {
            if (category.getCategoryType().equals(CategoryType.SAVINGS)) {
                savingsCategories.add(category);
            }
        }
        return savingsCategories;
    }

    public void updateMonthlyIncome(Long userId, Double income) throws NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.getBudget().setMonthlyIncome(BigDecimal.valueOf(income));
            userRepo.save(user);
        } else {
            throw new NoSuchUserException("User not found.");
        }
    }
}
