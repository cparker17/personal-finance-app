package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.Account;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.models.UserFactory;
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

    public Account createAccount(User user, Account account) {
        addAccountToUser(user, account);
        return accountRepo.save(account);
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
}
