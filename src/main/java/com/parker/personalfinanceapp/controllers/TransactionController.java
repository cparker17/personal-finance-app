package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.exceptions.NoSuchTransactionException;
import com.parker.personalfinanceapp.models.transactions.Transaction;
import com.parker.personalfinanceapp.models.transactions.TransactionFactory;
import com.parker.personalfinanceapp.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @RequestMapping
    public String viewTransactionSelectionPage(Model model) {
        return "transaction-view";
    }

    @RequestMapping("/form/{transactionType}")
    public String viewNewTransactionPage(Model model, @PathVariable(name="transactionType") String transactionType)
            throws NoSuchTransactionException {
        model.addAttribute("transaction", TransactionFactory.createTransaction(transactionType));
        return "transaction-form";
    }

    @RequestMapping("/new/{accountId}")
    public String createTransaction(Model model, @PathVariable(name="accountId") Long accountId,
                                @RequestParam Transaction transaction) throws NoSuchAccountException {
        transactionService.createTransaction(accountId, transaction);
        model.addAttribute("transaction", transaction);
        return "transaction-view";
    }

    @RequestMapping("/edit/{transactionId}")
    public String viewEditTransactionPage(Model model, @PathVariable(name="transactionId") Long transactionId)
            throws NoSuchTransactionException {
        model.addAttribute("transaction", transactionService.getTransaction(transactionId));
        return "transaction-edit";
    }

    @RequestMapping("/update/{transactionId}")
    public String updateTransaction(Model model, @PathVariable(name="transactionId") Long transactionId,
                                @RequestParam Transaction transaction) throws NoSuchTransactionException {
        model.addAttribute("transaction", transactionService.updateTransaction(transactionId, transaction));
        return "transaction-view";
    }

    @RequestMapping("/delete/{transactionId}")
    public String deleteDeposit( @PathVariable(name="transactionId") Long transactionId)
            throws NoSuchTransactionException {
        transactionService.deleteTransaction(transactionId);
        return "redirect:/";
    }
}
