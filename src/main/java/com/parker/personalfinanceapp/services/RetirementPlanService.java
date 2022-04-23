package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchRetirementPlanException;
import com.parker.personalfinanceapp.models.RetirementPlan;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.repositories.RetirementPlanRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RetirementPlanService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    RetirementPlanRepo retirementPlanRepo;

    public RetirementPlan createRetirementPlan(User user, RetirementPlan retirementPlan) {
        addRetirementPlanToUser(user, retirementPlan);
        return retirementPlanRepo.save(retirementPlan);
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

    public RetirementPlan getRetirementPlan(User user) {
        return user.getRetirementPlan();
    }

    public RetirementPlan updateRetirementPlan(User user, RetirementPlan retirementPlan)
            throws NoSuchRetirementPlanException {
        retirementPlanRepo.delete(getRetirementPlanFromDB(user));
        addRetirementPlanToUser(user, retirementPlan);
        return retirementPlanRepo.save(retirementPlan);
    }

    public void deleteRetirementPlan(User user) throws NoSuchRetirementPlanException {
        retirementPlanRepo.delete(getRetirementPlanFromDB(user));
        user.setRetirementPlan(null);
        userRepo.save(user);
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
