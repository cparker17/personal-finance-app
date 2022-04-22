package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.accounts.Loan;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
import com.parker.personalfinanceapp.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    LoanService loanService;

    @RequestMapping("/form")
    public String viewNewLoanPage(Model model) {
        model.addAttribute("loan", new Loan());
        return "loan-new";
    }

    @RequestMapping("/new")
    public String createLoan(Model model, Authentication auth, @RequestParam Loan loan)
            throws NoSuchUserException {
        model.addAttribute("loan", loanService.createLoan(UserFactory.createUser(auth).getId(), loan));
        return "loan-view";
    }

    @RequestMapping("/edit/{loanId}")
    public String viewEditLoanPage(Model model, @PathVariable(name="loanId") Long loanId)
            throws NoSuchAccountException {
        model.addAttribute("loan", loanService.getLoan(loanId));
        return "loan-edit";
    }

    @RequestMapping("/update")
    public String updateLoan(Model model, @RequestParam Loan loan)
            throws NoSuchAccountException {
        model.addAttribute("loan", loanService.updateLoan(loan));
        return "loan-view";
    }

    @RequestMapping("/delete")
    public String deleteLoan(@RequestParam Loan loan)
            throws NoSuchAccountException {
        loanService.deleteLoan(loan);
        return "redirect:/";
    }
}
