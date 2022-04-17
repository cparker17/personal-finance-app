package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.models.RetirementPlan;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
import com.parker.personalfinanceapp.services.RetirementPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/retirement")
public class RetirementPlanController {
    @Autowired
    RetirementPlanService retirementPlanService;

    @RequestMapping("/form")
    public String viewNewRetirementPlanPage(Model model) {
        model.addAttribute("plan", new RetirementPlan());
        return "retirement-plan-new";
    }

    @RequestMapping("/new")
    public String createRetirementPlan(Model model, Authentication auth, @RequestParam RetirementPlan retirementPlan) {
        User user = UserFactory.createUser(auth);
        model.addAttribute("plan",
                retirementPlanService.createRetirementPlan(user.getId(), retirementPlan));
        return "retirement-plan-view";
    }

    @RequestMapping("/edit")
    public String viewEditRetirementPlanForm(Model model, Authentication auth) {
        User user = UserFactory.createUser(auth);
        model.addAttribute("plan", retirementPlanService.getRetirementPlan(user.getId()));
        return "retirement-plan-edit";
    }

    @RequestMapping("/update")
    public String updateRetirementPlan(Model model, Authentication auth, @RequestParam RetirementPlan retirementPlan) {
        User user = UserFactory.createUser(auth);
        model.addAttribute("plan", retirementPlanService.updateRetirementPlan(user.getId(), retirementPlan));
        return "retirement-plan-view";
    }

    @RequestMapping("/delete")
    public String deleteRetirementPlan(Model model, Authentication auth) {
        User user = UserFactory.createUser(auth);
        retirementPlanService.deleteRetirementPlan(user.getId());
        return "redirect:/";
    }
}
