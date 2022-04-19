package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
import com.parker.personalfinanceapp.services.user.UserService;
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
        User user = UserFactory.createUser(auth);
        model.addAttribute("user", userService.getUserInfo(user.getId()));
        return "user-view";
    }

    @RequestMapping("/edit")
    public String viewEditUserInfoPage(Model model, Authentication auth) throws NoSuchUserException {
        User user = UserFactory.createUser(auth);
        model.addAttribute("user", userService.getUserInfo(user.getId()));
        return "user-edit";
    }

    @RequestMapping("/update")
    public String updateUserInfo(Model model, Authentication auth, @RequestParam User newUser)
            throws NoSuchUserException {
        User user = UserFactory.createUser(auth);
        model.addAttribute("user", userService.updateUserInfo(user.getId(), newUser));
        return "user-view";
    }
}
