package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.exceptions.NoSuchReportException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.accounts.Account;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
import com.parker.personalfinanceapp.services.accounts.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

    @RequestMapping("/form/{accountType}")
    public String viewNewAccountPage(Model model, @PathVariable(name="accountType") String accountType)
            throws NoSuchAccountException {
        model.addAttribute("account", accountService.getAccountType(accountType));
        switch (accountType) {
            case "BankAccount": return "bank-account-new";
            case "LoanAccount": return "loan-account-new";
            case "RetirementAccount": return "retirement-account-new";
            default: throw new NoSuchAccountException("Account does not exist.");
        }
    }

    @RequestMapping("/new/{accountType}")
    public String createAccount(Model model, Authentication auth,
                                @PathVariable(name="accountType") String accountType,
                                @RequestParam Account account) throws NoSuchAccountException, NoSuchUserException {
        User user = UserFactory.createUser(auth);
        model.addAttribute("account",
                accountService.createAccount(user.getId(), accountType, account));
        return getAccountView(accountType);
    }

    private String getAccountView(@PathVariable(name = "accountType") String accountType) throws NoSuchAccountException {
        switch (accountType) {
            case "BankAccount": return "bank-account-view";
            case "LoanAccount": return "loan-account-view";
            case "RetirementAccount": return "retirement-account-view";
            default: throw new NoSuchAccountException("Account does not exist.");
        }
    }

    @RequestMapping("/edit/{accountId}/{accountType}")
    public String viewEditAccountPage(Model model, @PathVariable(name="accountId") Long accountId,
                                      @PathVariable(name="accountType") String accountType)
            throws NoSuchAccountException {
        model.addAttribute("account", accountService.getAccount(accountId, accountType));
        switch (accountType) {
            case "BankAccount": return "bank-account-edit";
            case "LoanAccount": return "loan-account-edit";
            case "RetirementAccount": return "retirement-account-edit";
            default: throw new NoSuchAccountException("Account does not exist.");
        }
    }

    @RequestMapping("/update/{accountType}")
    public String updateAccount(Model model, @RequestParam Account account,
                                @PathVariable(name="accountType") String accountType)
            throws NoSuchAccountException {
        model.addAttribute("account", accountService.updateAccount(account, accountType));
        return getAccountView(accountType);
    }

    @RequestMapping("/delete/{accountType}")
    public String deleteAccount(@RequestParam Account account,
                                @PathVariable(name="accountType") String accountType)
            throws NoSuchAccountException {
        accountService.deleteAccount(account, accountType);
        return "redirect:/";
    }
}
