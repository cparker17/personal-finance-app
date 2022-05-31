package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchGoalException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.exceptions.PersonalFinanceAppException;
import com.parker.personalfinanceapp.models.Account;
import com.parker.personalfinanceapp.models.Category;
import com.parker.personalfinanceapp.models.Goal;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.repositories.GoalRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class GoalService {
    @Autowired
    GoalRepo goalRepo;

    @Autowired
    UserRepo userRepo;

    @Transactional
    public Goal createGoal(User user, Goal goal) throws NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(user.getId());
        if (userOptional.isPresent()) {
            user = userOptional.get();
            setUserGoal(user, goal);
            return goalRepo.save(goal);
        } else {
            throw new NoSuchUserException("User not found.");
        }
    }

    private void setUserGoal(User user, Goal goal) {
        user.setGoal(goal);
        userRepo.save(user);
    }

    @Transactional
    public Goal getGoal(User tempUser) throws PersonalFinanceAppException {
        Optional<User> userOptional = userRepo.findById(tempUser.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Goal goal = user.getGoal();
            if (goal == null) {
                throw new NoSuchGoalException("Custom goal not found.  Please return to dashboard to create a goal.");
            }
            BigDecimal monthlyBudgetAmount = getMonthlySavingsAmount(user);
            BigDecimal currentAccountBalance = getCurrentAccountBalance(user);
            goal.setMonthsFromGoal((goal.getAmountNeeded().subtract(currentAccountBalance).divide(monthlyBudgetAmount)).intValue());
            return goal;
        } else {
            throw new NoSuchUserException("User not found.");
        }
    }

    public Goal updateGoal(User user, Goal newGoal) throws NoSuchGoalException {
        goalRepo.delete(getGoalFromDB(user));
        setUserGoal(user, newGoal);
        return goalRepo.save(newGoal);
    }

    public void deleteGoal(User user) throws NoSuchGoalException {
        goalRepo.delete(getGoalFromDB(user));
        setUserGoal(user, null);
    }

    private Goal getGoalFromDB(User user) throws NoSuchGoalException {
        Optional<Goal> goalOptional = goalRepo.findById(user.getGoal().getId());
        if (goalOptional.isPresent()) {
            return goalOptional.get();
        } else {
            throw new NoSuchGoalException("Goal does not exist.");
        }
    }

    private BigDecimal getMonthlySavingsAmount (User user) throws PersonalFinanceAppException {
        List<Category> categories = user.getBudget().getCategories();
        for (Category category : categories) {
            if (category.getName().equals("General Savings")) {
                return category.getMonthlyBudgetAmt();
            }
        }
        throw new PersonalFinanceAppException("General Savings category does not exist.");
    }

    private BigDecimal getCurrentAccountBalance (User user) {
        List<Account> accounts = user.getAccounts();
        BigDecimal currentAccountBalance = BigDecimal.ZERO;
        for (Account account : accounts) {
            if (account.getType().equals("bankAccount")) {
                currentAccountBalance = currentAccountBalance.add(account.getCurrentBalance());
            }
        }
        return currentAccountBalance;
    }
}
