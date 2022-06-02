package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchRetirementPlanException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.RetirementPlan;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.models.UserFactory;
import com.parker.personalfinanceapp.services.RetirementPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/view")
    public String viewRetirementPlan(Model model, Authentication auth) throws NoSuchUserException {
        model.addAttribute("plan", retirementPlanService.getRetirementPlan(UserFactory.createUser(auth)));
        return "retirement-plan-view";
    }

    @RequestMapping("/new")
    public String createRetirementPlan(Model model, Authentication auth, @ModelAttribute RetirementPlan retirementPlan)
            throws NoSuchUserException {
        model.addAttribute("plan",
                retirementPlanService.createRetirementPlan(UserFactory.createUser(auth), retirementPlan));
        return "retirement-plan-view";
    }

    @RequestMapping("/edit")
    public String viewEditRetirementPlanForm(Model model, Authentication auth) throws NoSuchUserException {
        model.addAttribute("plan",
                retirementPlanService.getRetirementPlan(UserFactory.createUser(auth)));
        return "retirement-plan-edit";
    }

    @RequestMapping("/update")
    public String updateRetirementPlan(Model model, Authentication auth, @ModelAttribute RetirementPlan retirementPlan)
            throws NoSuchRetirementPlanException, NoSuchUserException {
        User user = UserFactory.createUser(auth);
        retirementPlanService.updateRetirementPlan(user, retirementPlan);
        model.addAttribute("plan", retirementPlanService.getRetirementPlan(user));
        return "retirement-plan-view";
    }

    @RequestMapping("/delete")
    public String deleteRetirementPlan(Authentication auth)
            throws NoSuchRetirementPlanException, NoSuchUserException {
        retirementPlanService.deleteRetirementPlan(UserFactory.createUser(auth));
        return "redirect:/dashboard";
    }
}
