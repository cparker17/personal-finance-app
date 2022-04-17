package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchBudgetException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.budget.Budget;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
import com.parker.personalfinanceapp.services.budget.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/budget")
public class BudgetController {
    @Autowired
    BudgetService budgetService;

    @RequestMapping("/form")
    public String viewNewBudgetPage(Model model) {
        model.addAttribute("budget", new Budget());
        return "budget-new";
    }

    @RequestMapping("/new")
    public String createBudget(Model model, Authentication auth, @RequestParam Budget budget)
            throws NoSuchUserException {
        User user = UserFactory.createUser(auth);
        model.addAttribute("budget", budgetService.createBudget(user.getId(), budget));
        return "budget-view";
    }

    @RequestMapping("/view")
    public String viewBudget(Model model, Authentication auth) throws NoSuchUserException {
        User user = UserFactory.createUser(auth);
        model.addAttribute("budget", budgetService.getBudget(user.getId()));
        return "budget-view";
    }

    @RequestMapping("/edit")
    public String viewEditBudgetForm(Model model, Authentication auth) throws NoSuchUserException {
        User user = UserFactory.createUser(auth);
        model.addAttribute("budget", budgetService.getBudget(user.getId()));
        return "budget-edit";
    }

    @RequestMapping("/update")
    public String updateBudget(Model model, Authentication auth, @RequestParam Budget budget)
            throws NoSuchUserException, NoSuchBudgetException {
        User user = UserFactory.createUser(auth);
        model.addAttribute("budget", budgetService.updateBudget(user.getId(), budget));
        return "budget-view";
    }

    @RequestMapping("/delete")
    public String deleteBudget(Authentication auth) throws NoSuchBudgetException, NoSuchUserException {
        User user = UserFactory.createUser(auth);
        budgetService.deleteBudget(user.getId());
        return "redirect:/";
    }
}
