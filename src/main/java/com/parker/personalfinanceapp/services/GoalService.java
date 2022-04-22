package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchGoalException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.Goal;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
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

    public Goal createGoal(Long userId, Goal goal) throws NoSuchUserException {
        User user = UserFactory.getUser(userId);
        user.setGoal(goal);
        userRepo.save(user);
        return goalRepo.save(goal);
    }

    public Goal getGoal(Long userId) throws NoSuchUserException {
        return UserFactory.getUser(userId).getGoal();
    }

    public Goal updateGoal(Long userId, Goal newGoal) throws NoSuchUserException, NoSuchGoalException {
        User user = UserFactory.getUser(userId);
        goalRepo.delete(getGoalFromDB(user));
        user.setGoal(newGoal);
        userRepo.save(user);
        return goalRepo.save(newGoal);
    }

    public void deleteGoal(Long userId) throws NoSuchGoalException, NoSuchUserException {
        User user = UserFactory.getUser(userId);
        goalRepo.delete(getGoalFromDB(user));
        user.setGoal(null);
        userRepo.save(user);
    }

    private Goal getGoalFromDB(User user) throws NoSuchUserException, NoSuchGoalException {
        Optional<Goal> goalOptional = goalRepo.findById(user.getGoal().getId());
        if (goalOptional.isPresent()) {
            return goalOptional.get();
        } else {
            throw new NoSuchGoalException("Goal does not exist.");
        }
    }
}
