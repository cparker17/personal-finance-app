package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.*;
import com.parker.personalfinanceapp.repositories.AccountRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    AccountRepo accountRepo;

    public Account getAccount(Long accountId) throws NoSuchAccountException {
        return getAccountFromDB(accountId);
    }

    public Account updateAccount(Account newAccount) throws NoSuchAccountException {
        Account account = getAccountFromDB(newAccount.getId());
        account.setType(newAccount.getType());
        account.setInstitutionName(newAccount.getInstitutionName());
        account.setAccountNum(newAccount.getAccountNum());
        account.setStartBalance(newAccount.getCurrentBalance());
        account.setCurrentBalance(newAccount.getCurrentBalance());
        return accountRepo.save(newAccount);
    }

    public void deleteAccount(Long accountId) throws NoSuchAccountException {
        accountRepo.delete(getAccountFromDB(accountId));
    }

    public void createAccount(Long userId, Account account) {
        account.setStartBalance(account.getCurrentBalance());
        accountRepo.save(account);
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            addAccountToUser(user, account);
        }
    }

    private void addAccountToUser(User user, Account account) {
        user.addAccount(account);
        userRepo.save(user);
    }

    private Account getAccountFromDB(Long accountId) throws NoSuchAccountException {
        Optional<Account> accountOptional = accountRepo.findById(accountId);
        if (accountOptional.isPresent()) {
            return accountOptional.get();
        } else {
            throw new NoSuchAccountException("Account does not exist.");
        }
    }

    public List<Account> getAllBankAccounts(Long userId) throws NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            List<Account> bankAccounts = new ArrayList<>();
            for (Account account : userOptional.get().getAccounts()) {
                if (account.getType().equals("bankAccount")) {
                    bankAccounts.add(account);
                }
            }
            return bankAccounts;
        } else {
            throw new NoSuchUserException("User does not exist.");
        }
    }

    public List<Account> getAllRetirementAccounts(Long userId) throws NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            List<Account> retirementAccounts = new ArrayList<>();
            for (Account account : userOptional.get().getAccounts()) {
                if (account.getType().equals("retirementAccount")) {
                    retirementAccounts.add(account);
                }
            }
            return retirementAccounts;
        } else {
            throw new NoSuchUserException("User does not exist.");
        }
    }

    public List<Account> getAllAccounts(Long userId, String transactionType) throws NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get().getAccounts();

        } else {
            throw new NoSuchUserException("user not found.");
        }
    }
}
