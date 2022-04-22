package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.accounts.Account;
import com.parker.personalfinanceapp.models.accounts.AccountFactory;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
import com.parker.personalfinanceapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @RequestMapping("/form/{accountType}")
    public String viewNewAccountPage(Model model, @PathVariable(name="accountType") String accountType)
            throws NoSuchAccountException {
        model.addAttribute("account", AccountFactory.createAccount(accountType));
        return "account-new";
    }

    @RequestMapping("/new")
    public String createAccount(Model model, Authentication auth, @RequestParam Account account)
            throws NoSuchUserException {
        User user = UserFactory.createUser(auth);
        model.addAttribute("account",
                accountService.createAccount(user.getId(), account));
        return "account-view";
    }

    @RequestMapping("/edit/{accountId}")
    public String viewEditAccountPage(Model model, @PathVariable(name="accountId") Long accountId)
            throws NoSuchAccountException {
        model.addAttribute("account", accountService.getAccount(accountId));
        return "account-edit";
    }

    @RequestMapping("/update")
    public String updateAccount(Model model, @RequestParam Account account)
            throws NoSuchAccountException {
        model.addAttribute("account", accountService.updateAccount(account));
        return "account-view";
    }

    @RequestMapping("/delete")
    public String deleteAccount(@RequestParam Account account)
            throws NoSuchAccountException {
        accountService.deleteAccount(account);
        return "redirect:/";
    }
}
