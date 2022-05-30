package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.dto.IncomeWrapper;
import com.parker.personalfinanceapp.models.UserFactory;
import com.parker.personalfinanceapp.exceptions.NoSuchBudgetException;
import com.parker.personalfinanceapp.exceptions.NoSuchCategoryException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.*;
import com.parker.personalfinanceapp.services.BudgetService;
import com.parker.personalfinanceapp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/budget")
public class BudgetController {
    @Autowired
    BudgetService budgetService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/form")
    public String viewNewBudgetCategoryPage(Model model) {
        model.addAttribute("category", new Category());
        return "budget-new";
    }

    @RequestMapping("/new")
    public String addBudgetCategory(Authentication auth, @ModelAttribute Category category) {
        User user = UserFactory.createUser(auth);
        categoryService.createCategory(user.getId(), category);
        return "redirect:/budget/view";
    }

    @RequestMapping("/view")
    public String viewBudget(Model model, Authentication auth) throws NoSuchBudgetException, NoSuchUserException {
        User user = UserFactory.createUser(auth);
        Budget budget = budgetService.getBudget(user);
        model.addAttribute("budget", budget);
        model.addAttribute("needs", budgetService.getNeedsCategories(budget));
        model.addAttribute("wants", budgetService.getWantsCategories(budget));
        model.addAttribute("savings", budgetService.getSavingsCategories(budget));
        return "budget-view";
    }

    @RequestMapping("/edit/{id}")
    public String viewEditBudgetForm(Model model, @PathVariable(name="id") Long id) throws NoSuchCategoryException {
        model.addAttribute("category", categoryService.getCategory(id));
        return "budget-edit";
    }

    @RequestMapping("/update")
    public String updateBudget(@ModelAttribute(name="category") Category category)
            throws NoSuchBudgetException, NoSuchUserException {
        categoryService.updateCategory(category);
        return "redirect:/budget/view";
    }

    @RequestMapping("/income-form")
    public String displayIncomeForm(Model model) {
        model.addAttribute("income", new IncomeWrapper());
        return "income-form";
    }

    @RequestMapping("/new-income")
    public String updateMonthlyIncome(@ModelAttribute IncomeWrapper income, Authentication auth)
            throws NoSuchUserException {
        budgetService.updateMonthlyIncome(UserFactory.createUser(auth).getId(), income.getMonthlyIncome());
        return "redirect:/budget/view";
    }

    @RequestMapping("/delete")
    public String deleteBudget(Authentication auth) throws NoSuchBudgetException, NoSuchUserException {
        budgetService.deleteBudget(UserFactory.createUser(auth));
        return "redirect:/";
    }

    @RequestMapping("/verify")
    public String verifyBudget(Model model, Authentication auth) throws NoSuchUserException {
        model.addAttribute("ratios", budgetService.verifyBudget(UserFactory.createUser(auth).getId()));
        return "budget-verify";
    }
}
