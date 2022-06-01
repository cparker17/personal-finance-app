package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchRetirementPlanException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.RetirementPlan;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.repositories.RetirementPlanRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private void addRetirementPlanToUser(User user, RetirementPlan retirementPlan) {
        user.setRetirementPlan(retirementPlan);
        retirementPlan.setIsOnTrack(isRetirementOnTrack(retirementPlan));
        userRepo.save(user);
    }

    private boolean isRetirementOnTrack(RetirementPlan retirementPlan) {
        //algorithm to determine if retirement is on track or not
        return true;
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
    public RetirementPlan updateRetirementPlan(User user, RetirementPlan retirementPlan)
            throws NoSuchRetirementPlanException, NoSuchUserException {
        retirementPlanRepo.delete(getRetirementPlanFromDB(user));
        Optional<User> userOptional = userRepo.findById(user.getId());
        if (userOptional.isPresent()) {
            user = userOptional.get();
            addRetirementPlanToUser(user, retirementPlan);
            return retirementPlanRepo.save(retirementPlan);
        } else {
            throw new NoSuchUserException("User not found.");
        }
    }

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
