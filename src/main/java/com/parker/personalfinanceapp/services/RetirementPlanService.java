package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchRetirementPlanException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.RetirementPlan;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
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

    public RetirementPlan createRetirementPlan(Long userId, RetirementPlan retirementPlan) throws NoSuchUserException {
        User user = UserFactory.getUser(userId);
        user.setRetirementPlan(retirementPlan);
        retirementPlan.setIsOnTrack(isRetirementOnTrack(retirementPlan));
        userRepo.save(user);
        return retirementPlanRepo.save(retirementPlan);
    }

    private boolean isRetirementOnTrack(RetirementPlan retirementPlan) {
        //algorithm to determine if retirement is on track or not
        return true;
    }

    public RetirementPlan getRetirementPlan(Long userID) throws NoSuchUserException {
        return UserFactory.getUser(userID).getRetirementPlan();
    }

    public RetirementPlan updateRetirementPlan(Long userId, RetirementPlan retirementPlan)
            throws NoSuchRetirementPlanException, NoSuchUserException {
        User user = UserFactory.getUser(userId);
        retirementPlanRepo.delete(getRetirementPlanFromDB(user));
        user.setRetirementPlan(retirementPlan);
        retirementPlan.setIsOnTrack(isRetirementOnTrack(retirementPlan));
        userRepo.save(user);
        return retirementPlanRepo.save(retirementPlan);
    }

    public void deleteRetirementPlan(Long userId) throws NoSuchRetirementPlanException, NoSuchUserException {
        User user = UserFactory.getUser(userId);
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
