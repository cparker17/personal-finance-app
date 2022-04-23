package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchRetirementPlanException;
import com.parker.personalfinanceapp.models.RetirementPlan;
import com.parker.personalfinanceapp.models.UserFactory;
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
        model.addAttribute("plan",
                retirementPlanService.createRetirementPlan(UserFactory.createUser(auth), retirementPlan));
        return "retirement-plan-view";
    }

    @RequestMapping("/edit")
    public String viewEditRetirementPlanForm(Model model, Authentication auth) {
        model.addAttribute("plan",
                retirementPlanService.getRetirementPlan(UserFactory.createUser(auth)));
        return "retirement-plan-edit";
    }

    @RequestMapping("/update")
    public String updateRetirementPlan(Model model, Authentication auth, @RequestParam RetirementPlan retirementPlan)
            throws NoSuchRetirementPlanException {
        model.addAttribute("plan",
                retirementPlanService.updateRetirementPlan(UserFactory.createUser(auth), retirementPlan));
        return "retirement-plan-view";
    }

    @RequestMapping("/delete")
    public String deleteRetirementPlan(Authentication auth)
            throws NoSuchRetirementPlanException {
        retirementPlanService.deleteRetirementPlan(UserFactory.createUser(auth));
        return "redirect:/";
    }
}
