package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.models.UserFactory;
import com.parker.personalfinanceapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

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
    public String updateUserInfo(Model model, Authentication auth, @RequestParam User newUser) {
        model.addAttribute("user",
                userService.updateUserInfo(UserFactory.createUser(auth), newUser));
        return "user-view";
    }
}
