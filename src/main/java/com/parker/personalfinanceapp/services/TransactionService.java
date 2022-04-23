package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.exceptions.NoSuchTransactionException;
import com.parker.personalfinanceapp.models.Account;
import com.parker.personalfinanceapp.models.Transaction;
import com.parker.personalfinanceapp.repositories.AccountRepo;
import com.parker.personalfinanceapp.repositories.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    AccountRepo accountRepo;

    @Autowired
    TransactionRepo transactionRepo;

    public void createTransaction(Long accountId, Transaction transaction) throws NoSuchAccountException {
        Optional<Account> accountOptional = accountRepo.findById(accountId);
        if (accountOptional.isPresent()) {
            accountOptional.get().addTransaction(transaction);
        } else {
            throw new NoSuchAccountException("Account does not exist.");
        }
    }

    public Transaction getTransaction(Long transactionId) throws NoSuchTransactionException {
        return getTransactionFromDB(transactionId);
    }

    public Transaction updateTransaction(Long transactionId, Transaction newTransaction)
            throws NoSuchTransactionException {
        Transaction transaction = getTransactionFromDB(transactionId);
        transaction.setAmount(newTransaction.getAmount());
        transaction.setTransactionDate(newTransaction.getTransactionDate());
        return transactionRepo.save(transaction);
    }

    public void deleteTransaction(Long transactionId) throws NoSuchTransactionException {
        transactionRepo.delete(getTransactionFromDB(transactionId));
    }

    private Transaction getTransactionFromDB(Long transactionId) throws NoSuchTransactionException {
        Optional<Transaction> transactionOptional = transactionRepo.findById(transactionId);
        if (transactionOptional.isPresent()) {
            return transactionOptional.get();
        } else {
            throw new NoSuchTransactionException("Transaction does not exist.");
        }
    }
}
