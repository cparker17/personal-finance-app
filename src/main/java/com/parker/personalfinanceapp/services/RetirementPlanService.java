package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchRetirementPlanException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.Account;
import com.parker.personalfinanceapp.models.Category;
import com.parker.personalfinanceapp.models.RetirementPlan;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.repositories.RetirementPlanRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class RetirementPlanService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    RetirementPlanRepo retirementPlanRepo;

    @Transactional
    public RetirementPlan createRetirementPlan(User user, RetirementPlan retirementPlan) throws NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(user.getId());
        if (userOptional.isPresent()) {
            user = userOptional.get();
            addRetirementPlanToUser(user, retirementPlan);
            return retirementPlanRepo.save(retirementPlan);
        } else {
            throw new NoSuchUserException("User not found.");
        }
    }

    private void addRetirementPlanToUser(User user, RetirementPlan retirementPlan) throws NoSuchUserException {
        user.setRetirementPlan(retirementPlan);
        retirementPlan.setIsOnTrack(isRetirementOnTrack(user, retirementPlan));
        userRepo.save(user);
    }

    private boolean isRetirementOnTrack(User user, RetirementPlan retirementPlan) throws NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(user.getId());
        if (userOptional.isPresent()) {
            user = userOptional.get();
            BigDecimal currentRetirementSavings = BigDecimal.ZERO;
            for (Account account : user.getAccounts()) {
                if (account.getType().equals("retirementAccount")) {
                    currentRetirementSavings = currentRetirementSavings.add(account.getCurrentBalance());
                }
            }
            Integer yearsToRetirementAge = retirementPlan.getRetirementAge() - retirementPlan.getCurrentAge();
            BigDecimal amountNeededToRetire = retirementPlan.getAmountNeeded().subtract(currentRetirementSavings);
            BigDecimal retirementBudget = BigDecimal.ZERO;
            for (Category category : user.getBudget().getCategories()) {
                if (category.getName().equals("Retirement")) {
                    retirementBudget = category.getMonthlyBudgetAmt();
                }
            }
            Double yearsToRetire = amountNeededToRetire.divide(retirementBudget).doubleValue() / 12;
            retirementPlan.setAdditionalYearsToRetirement((int) (yearsToRetire - yearsToRetirementAge));
            return retirementPlan.getAdditionalYearsToRetirement() <= 0;
        } else {
            throw new NoSuchUserException("User not found.");
        }
    }

    public RetirementPlan getRetirementPlan(User user) throws NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(user.getId());
        if (userOptional.isPresent()) {
            user = userOptional.get();
            return user.getRetirementPlan();
        } else {
            throw new NoSuchUserException("User not found.");
        }
    }

    @Transactional
    public void updateRetirementPlan(User user, RetirementPlan retirementPlan)
            throws NoSuchRetirementPlanException, NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(user.getId());
        if (userOptional.isPresent()) {
            user = userOptional.get();
            deleteRetirementPlan(user);
            addRetirementPlanToUser(user, retirementPlan);
            retirementPlanRepo.save(retirementPlan);
        } else {
            throw new NoSuchUserException("User not found.");
        }
    }

    @Transactional
    public void deleteRetirementPlan(User user) throws NoSuchRetirementPlanException, NoSuchUserException {
        retirementPlanRepo.delete(getRetirementPlanFromDB(user));
        Optional<User> userOptional = userRepo.findById(user.getId());
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.setRetirementPlan(null);
            userRepo.save(user);
        } else {
            throw new NoSuchUserException("User not found.");
        }
    }

    private RetirementPlan getRetirementPlanFromDB(User user) throws NoSuchRetirementPlanException {
        Optional<RetirementPlan> retirementPlanOptional = retirementPlanRepo.findById(user.getRetirementPlan().getId());
        if (retirementPlanOptional.isPresent()) {
            return retirementPlanOptional.get();
        } else {
            throw new NoSuchRetirementPlanException("Retirement plan does not exist.");
        }
    }
}
