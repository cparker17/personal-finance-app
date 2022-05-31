package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.models.TransactionTypeFactory;
import com.parker.personalfinanceapp.models.UserFactory;
import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.exceptions.NoSuchTransactionException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.*;
import com.parker.personalfinanceapp.services.AccountService;
import com.parker.personalfinanceapp.services.LoanService;
import com.parker.personalfinanceapp.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountService accountService;

    @Autowired
    LoanService loanService;

    @RequestMapping("/view/{transactionType}")
    public String viewTransactionSelectionPage(Model model,
                                               @PathVariable(name="transactionType") String transactionType,
                                               Authentication auth) throws NoSuchUserException {
        User user = UserFactory.createUser(auth);
        model.addAttribute("transactions", transactionService.getAllTransactions(user.getId(),
                transactionType));
        return "transaction-view";
    }

    @RequestMapping("/form/{transactionType}")
    public String viewNewTransactionPage(Model model,
                                         @PathVariable(name="transactionType") String transactionType,
                                         Authentication auth) throws NoSuchUserException {
        Long userId = UserFactory.createUser(auth).getId();
        Transaction transaction = new Transaction();
        model.addAttribute("transaction", transaction);
        if (transactionType.equals("loanPayment")) {
            model.addAttribute("accounts", loanService.getAllLoans(userId));
        } else {
            model.addAttribute("accounts", accountService.getAllAccounts(userId, transactionType));
        }
        return "transaction-form";
    }

    @RequestMapping("/new")
    public String createTransaction(@ModelAttribute Transaction transaction)
            throws NoSuchAccountException {
        transactionService.createTransaction(transaction);
        return "redirect:/dashboard";
    }

    @RequestMapping("/edit/{transactionId}")
    public String viewEditTransactionPage(Model model, @PathVariable(name="transactionId") Long transactionId)
            throws NoSuchTransactionException {
        model.addAttribute("transaction", transactionService.getTransaction(transactionId));
        return "transaction-edit";
    }

    @RequestMapping("/update/{transactionId}")
    public String updateTransaction(Model model, @PathVariable(name="transactionId") Long transactionId,
                                @ModelAttribute Transaction transaction) throws NoSuchTransactionException {
        model.addAttribute("transaction", transactionService.updateTransaction(transactionId, transaction));
        return "transaction-view";
    }

    @RequestMapping("/delete/{transactionId}")
    public String deleteTransaction( @PathVariable(name="transactionId") Long transactionId)
            throws NoSuchTransactionException {
        transactionService.deleteTransaction(transactionId);
        return "redirect:/";
    }
}
