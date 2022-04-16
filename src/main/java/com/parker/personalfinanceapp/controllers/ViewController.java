package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.models.user.SecurityUser;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        return "index";
    }

    @GetMapping("/register")
    public String viewRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/sign-in")
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
}
