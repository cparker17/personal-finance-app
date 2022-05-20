package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchBudgetException;
import com.parker.personalfinanceapp.exceptions.NoSuchCategoryException;
import com.parker.personalfinanceapp.models.Budget;
import com.parker.personalfinanceapp.models.Category;
import com.parker.personalfinanceapp.models.UserFactory;
import com.parker.personalfinanceapp.services.BudgetService;
import com.parker.personalfinanceapp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/budget")
public class BudgetController {
    @Autowired
    BudgetService budgetService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/form")
    public String viewNewBudgetPage(Model model) {
        model.addAttribute("budget", new Budget());
        return "budget-new";
    }

    @RequestMapping("/new")
    public String createBudget(Model model, Authentication auth, @RequestParam Budget budget) {
        model.addAttribute("budget",
                budgetService.createBudget(UserFactory.createUser(auth), budget));
        return "budget-view";
    }

    @RequestMapping("/view")
    public String viewBudget(Model model, Authentication auth) throws NoSuchBudgetException {
        model.addAttribute("budget", budgetService.getBudget(UserFactory.createUser(auth)));
        return "budget-view";
    }

    @RequestMapping("/edit/{id}")
    public String viewEditBudgetForm(Model model, @PathVariable(name="id") Long id) throws NoSuchCategoryException {
        model.addAttribute("category", categoryService.getCategory(id));
        return "budget-edit";
    }

    @RequestMapping("/update")
    public String updateBudget(Model model, Authentication auth, @ModelAttribute(name="category") Category category)
            throws NoSuchBudgetException {
        categoryService.updateCategory(category);
        model.addAttribute("budget", budgetService.getBudget(UserFactory.createUser(auth)));
        return "budget-view";
    }

    @RequestMapping("/delete")
    public String deleteBudget(Authentication auth) throws NoSuchBudgetException {
        budgetService.deleteBudget(UserFactory.createUser(auth));
        return "redirect:/";
    }
}
