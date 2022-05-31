package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.models.TransactionTypeFactory;
import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.exceptions.NoSuchTransactionException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.*;
import com.parker.personalfinanceapp.repositories.AccountRepo;
import com.parker.personalfinanceapp.repositories.LoanRepo;
import com.parker.personalfinanceapp.repositories.TransactionRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Autowired
    LoanRepo loanRepo;

    public void createTransaction(Transaction transaction, Long userId) throws NoSuchAccountException {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (transaction.getStringType().equals("loanPayment")) {
                Optional<Loan> loanOptional = loanRepo.findById(transaction.getAccountId());
                if (loanOptional.isPresent()) {
                    Loan loan = loanOptional.get();
                    transaction.setTransactionDate(LocalDate.now());
                    transaction.setType(TransactionTypeFactory.createTransactionType(transaction.getStringType()));
                    transactionRepo.save(transaction);
                    loan.addTransaction(transaction);
                    loanRepo.save(loan);
                    user.getTransactions().add(transaction);
                    userRepo.save(user);
                } else {
                    throw new NoSuchAccountException("Loan does not exist.");
                }
            } else {
                Optional<Account> accountOptional = accountRepo.findById(transaction.getAccountId());
                if (accountOptional.isPresent()) {
                    Account account = accountOptional.get();
                    transaction.setTransactionDate(LocalDate.now());
                    transaction.setType(TransactionTypeFactory.createTransactionType(transaction.getStringType()));
                    transactionRepo.save(transaction);
                    account.addTransaction(transaction);
                    accountRepo.save(account);
                    user.getTransactions().add(transaction);
                    userRepo.save(user);
                } else {
                    throw new NoSuchAccountException("Account does not exist.");
                }
            }
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
            for (Transaction transaction : user.getTransactions()) {
                if (transaction.getType().equals(TransactionTypeFactory.createTransactionType(transactionType))) {
                    transactions.add(transaction);
                }
            }
            return transactions;
        } else {
            throw new NoSuchUserException("User not found.");
        }
    }
}
