package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.models.UserFactory;
import com.parker.personalfinanceapp.models.Account;
import com.parker.personalfinanceapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @RequestMapping("/form")
    public String viewNewAccountPage(Model model) {
        model.addAttribute("account", new Account());
        return "account-new";
    }

    @RequestMapping("/new")
    public String createAccount(Authentication auth, @ModelAttribute Account account) {
        accountService.createAccount(UserFactory.createUser(auth).getId(), account);
        return "redirect:/dashboard";
    }

    @RequestMapping("/edit/{accountId}")
    public String viewEditAccountPage(Model model, @PathVariable(name = "accountId") Long accountId)
            throws NoSuchAccountException {
        model.addAttribute("account", accountService.getAccount(accountId));
        return "account-edit";
    }

    @RequestMapping("/update")
    public String updateAccount(@ModelAttribute Account account) throws NoSuchAccountException {
        accountService.updateAccount(account);
        return "redirect:/dashboard";
    }

    @RequestMapping("/delete/{accountId}")
    public String deleteAccount(@PathVariable(name="accountId") Long accountId) throws NoSuchAccountException {
        accountService.deleteAccount(accountId);
        return "redirect:/dashboard";
    }

    @RequestMapping("/view/{accountId}")
    public String viewAccountInfo(Model model, @PathVariable(name="accountId") Long accountId)
            throws NoSuchAccountException {
        model.addAttribute("account", accountService.getAccount(accountId));
        return "account-view";
    }
}
