package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchGoalException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.exceptions.PersonalFinanceAppException;
import com.parker.personalfinanceapp.models.Goal;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.models.UserFactory;
import com.parker.personalfinanceapp.services.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String createGoal(Model model, Authentication auth, @ModelAttribute Goal goal) throws NoSuchUserException {
        model.addAttribute("goal", goalService.createGoal(UserFactory.createUser(auth), goal));
        return "goal-view";
    }

    @RequestMapping("/view")
    public String viewGoal(Model model, Authentication auth) throws PersonalFinanceAppException {
        model.addAttribute("goal", goalService.getGoal(UserFactory.createUser(auth)));
        return "goal-view";
    }
    @RequestMapping("/edit")
    public String viewEditGoalPage(Model model, Authentication auth) throws PersonalFinanceAppException {
        model.addAttribute("goal", goalService.getGoal(UserFactory.createUser(auth)));
        return "goal-edit";
    }

    @RequestMapping("/update")
    public String updateGoal(Model model, Authentication auth, @ModelAttribute Goal goal)
            throws PersonalFinanceAppException {
        User user = UserFactory.createUser(auth);
        goalService.updateGoal(user, goal);
        model.addAttribute("goal", goalService.getGoal(user));
        return "goal-view";
    }

    @RequestMapping("/delete")
    public String deleteGoal(Authentication auth) throws NoSuchGoalException {
        goalService.deleteGoal(UserFactory.createUser(auth));
        return "redirect:/";
    }
}
