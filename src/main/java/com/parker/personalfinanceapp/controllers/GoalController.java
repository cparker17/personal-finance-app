package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchGoalException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.Goal;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
import com.parker.personalfinanceapp.services.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/goal")
public class GoalController {
    @Autowired
    GoalService goalService;

    @RequestMapping("/form")
    public String viewNewGoalPage(Model model) {
        model.addAttribute("goal", new Goal());
        return "goal-form";
    }

    @RequestMapping("/new")
    public String createGoal(Model model, Authentication auth, @RequestParam Goal goal) throws NoSuchUserException {
        model.addAttribute("goal", goalService.createGoal(UserFactory.createUser(auth).getId(), goal));
        return "goal-view";
    }

    @RequestMapping("/edit")
    public String viewEditGoalPage(Model model, Authentication auth) throws NoSuchUserException {
        model.addAttribute("goal", goalService.getGoal(UserFactory.createUser(auth).getId()));
        return "goal-edit";
    }

    @RequestMapping("/update")
    public String updateGoal(Model model, Authentication auth, @RequestParam Goal goal)
            throws NoSuchUserException, NoSuchGoalException {
        model.addAttribute("goal", goalService.updateGoal(UserFactory.createUser(auth).getId(), goal));
        return "goal-view";
    }

    @RequestMapping("/delete")
    public String deleteGoal(Authentication auth) throws NoSuchUserException, NoSuchGoalException {
        goalService.deleteGoal(UserFactory.createUser(auth).getId());
        return "redirect:/";
    }
}
