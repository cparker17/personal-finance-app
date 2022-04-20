package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchGoalException;
import com.parker.personalfinanceapp.exceptions.NoSuchTransactionException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.Goal;
import com.parker.personalfinanceapp.models.budget.Transaction;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
import com.parker.personalfinanceapp.services.budget.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @RequestMapping("/form")
    public String viewNewTransactionPage(Model model) {
        model.addAttribute("goal", new Goal());
        return "transaction-form";
    }

    @RequestMapping("/new")
    public String createTransaction(Model model, Authentication auth, @RequestParam Transaction transaction)
            throws NoSuchUserException {
        User user = UserFactory.createUser(auth);
        model.addAttribute("goal", transactionService.createTransaction(user.getId(), transaction));
        return "transaction-view";
    }

    @RequestMapping("/edit/{transactionId}")
    public String viewEditTransactionPage(Model model, Authentication auth,
                                          @PathVariable(name="transactionId") Long transactionId)
            throws NoSuchUserException {
        User user = UserFactory.createUser(auth);
        model.addAttribute("goal", transactionService.getTransaction(transactionId));
        return "transaction-edit";
    }

    @RequestMapping("/update")
    public String updateTransaction(Model model, Authentication auth, @RequestParam Transaction transaction)
            throws NoSuchUserException, NoSuchTransactionException {
        User user = UserFactory.createUser(auth);
        model.addAttribute("goal", transactionService.updateTransaction(user.getId(), transaction));
        return "transaction-view";
    }

    @RequestMapping("/delete/{transactionId}")
    public String deleteTransaction(Authentication auth, @PathVariable(name="transactionId") Long transactionId)
            throws NoSuchUserException, NoSuchTransactionException {
        User user = UserFactory.createUser(auth);
        transactionService.deleteTransaction(user.getId());
        return "redirect:/";
    }
}
