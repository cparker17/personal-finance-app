package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.DuplicateUserException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.models.UserFactory;
import com.parker.personalfinanceapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/new")
    public String registerAccount(@Valid @ModelAttribute(name="user") User user, Errors errors)
            throws DuplicateUserException {
        if (errors.hasErrors()) {
            return "register";
        }
        userService.registerAccount(user);
        return "register-success";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("message", "Invalid username and password.");
        return "login-error";
    }

    @RequestMapping("/view")
    public String viewUserInfo(Model model, Authentication auth) throws NoSuchUserException {
        model.addAttribute("user", userService.getUserInfo(UserFactory.createUser(auth)));
        return "user-view";
    }

    @RequestMapping("/edit")
    public String viewEditUserInfoPage(Model model, Authentication auth) throws NoSuchUserException {
        model.addAttribute("user", userService.getUserInfo(UserFactory.createUser(auth)));
        return "user-edit";
    }

    @RequestMapping("/update")
    public String updateUserInfo(Model model, Authentication auth, @ModelAttribute User newUser) {
        model.addAttribute("user",
                userService.updateUserInfo(UserFactory.createUser(auth), newUser));
        return "user-view";
    }
}
