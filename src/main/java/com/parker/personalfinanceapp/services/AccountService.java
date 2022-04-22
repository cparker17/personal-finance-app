package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.accounts.Account;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
import com.parker.personalfinanceapp.repositories.AccountRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        account.setAccountType(newAccount.getAccountType());
        account.setInstitutionName(newAccount.getInstitutionName());
        account.setAccountNum(newAccount.getAccountNum());
        account.setStartBalance(newAccount.getStartBalance());
        account.setCurrentBalance(newAccount.getCurrentBalance());
        return accountRepo.save(newAccount);
    }

    public void deleteAccount(Account account) throws NoSuchAccountException {
        accountRepo.delete(getAccountFromDB(account.getId()));
    }

    @Transactional
    public Account createAccount(Long userId, Account account)
            throws NoSuchUserException {
        User user = UserFactory.getUser(userId);
        user.addAccount(account);
        userRepo.save(user);
        return accountRepo.save(account);
    }

    private Account getAccountFromDB(Long accountId) throws NoSuchAccountException {
        Optional<Account> accountOptional = accountRepo.findById(accountId);
        if (accountOptional.isPresent()) {
            return accountOptional.get();
        } else {
            throw new NoSuchAccountException("Account does not exist.");
        }
    }
}
