package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchGoalException;
import com.parker.personalfinanceapp.models.Goal;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.repositories.GoalRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GoalService {
    @Autowired
    GoalRepo goalRepo;

    @Autowired
    UserRepo userRepo;

    public Goal createGoal(User user, Goal goal) {
        setUserGoal(user, goal);
        return goalRepo.save(goal);
    }

    private void setUserGoal(User user, Goal goal) {
        user.setGoal(goal);
        userRepo.save(user);
    }

    public Goal getGoal(User user) {
        return user.getGoal();
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
}
