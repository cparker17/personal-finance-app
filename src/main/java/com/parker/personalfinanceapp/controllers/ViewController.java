package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.SecurityUser;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.models.UserFactory;
import com.parker.personalfinanceapp.services.AccountService;
import com.parker.personalfinanceapp.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @Autowired
    LoanService loanService;

    @Autowired
    AccountService accountService;

    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }

    @PostMapping("/register")
    public String viewRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/login")
    public String viewSignInPage(Model model) {
        model.addAttribute("securityUser", new SecurityUser());
        return "login";
    }

    @GetMapping("/register-form")
    public String viewRegisterAccountPage(Model model) {
        model.addAttribute(new User());
        return "register";
    }

    @GetMapping("/update")
    public String viewUpdateUserPage(Authentication auth, Model model) {
        model.addAttribute("user", UserFactory.createUser(auth));
        return "update-user";
    }

    @GetMapping("/help")
    public String viewHelpPage() {
        return "help";
    }

    @GetMapping("/search")
    public String displaySearchResults(Model model) {
        return "search-results";
    }

    @GetMapping("/overview")
    public String displayOverview(Model model, Authentication auth) throws NoSuchUserException {
        //Long userId = UserFactory.createUser(auth).getId();
//        model.addAttribute("loans", loanService.getAllLoans(userId));
//        model.addAttribute("bankAccounts", accountService.getAllBankAccounts(userId));
//        model.addAttribute("retirementAccounts", accountService.getAllRetirementAccounts(userId));
        return "overview";
    }

    @GetMapping("/dashboard")
    public String displayDashboard(Model model, Authentication auth) {
        return "dashboard";
    }
}
