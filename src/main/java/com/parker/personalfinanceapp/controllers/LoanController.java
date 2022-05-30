package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.Loan;
import com.parker.personalfinanceapp.models.UserFactory;
import com.parker.personalfinanceapp.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String createLoan(Authentication auth, @ModelAttribute Loan loan) throws NoSuchUserException {
        loanService.createLoan(UserFactory.createUser(auth).getId(), loan);
        return "redirect:/dashboard";
    }

    @RequestMapping("/edit/{loanId}")
    public String viewEditLoanPage(Model model, @PathVariable(name="loanId") Long loanId)
            throws NoSuchAccountException {
        model.addAttribute("loan", loanService.getLoan(loanId));
        return "loan-edit";
    }

    @RequestMapping("/update")
    public String updateLoan(@ModelAttribute Loan loan)
            throws NoSuchAccountException {
        loanService.updateLoan(loan);
        return "redirect:/dashboard";
    }

    @RequestMapping("/delete/{loanId}")
    public String deleteLoan(@PathVariable(name="loanId") Long loanId)
            throws NoSuchAccountException {
        loanService.deleteLoan(loanId);
        return "redirect:/dashboard";
    }

    @RequestMapping("/view/{loanId}")
    public String viewLoanInfo(Model model, @PathVariable(name="loanId") Long accountId)
            throws NoSuchAccountException {
        model.addAttribute("loan", loanService.getLoan(accountId));
        return "loan-view";
    }
}
