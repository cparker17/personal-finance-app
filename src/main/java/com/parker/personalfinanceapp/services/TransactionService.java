package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.models.TransactionTypeFactory;
import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.exceptions.NoSuchTransactionException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.*;
import com.parker.personalfinanceapp.repositories.AccountRepo;
import com.parker.personalfinanceapp.repositories.TransactionRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    AccountRepo accountRepo;

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    UserRepo userRepo;

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

    public List<Transaction> getAllTransactions(Long userId, String transactionType) throws NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Transaction> transactions = new ArrayList<>();
            for (Category category : user.getBudget().getCategories()) {
                for (Transaction transaction : category.getTransactions()) {
                    if (transaction.getType().equals(TransactionTypeFactory.createTransactionType(transactionType))) {
                        transactions.add(transaction);
                    }
                }
            }
            return transactions;
        } else {
            throw new NoSuchUserException("User not found.");
        }
    }
}
