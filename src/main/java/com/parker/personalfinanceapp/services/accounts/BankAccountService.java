package com.parker.personalfinanceapp.services.accounts;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.accounts.Account;
import com.parker.personalfinanceapp.models.accounts.BankAccount;
import com.parker.personalfinanceapp.models.accounts.LoanAccount;
import com.parker.personalfinanceapp.models.accounts.RetirementAccount;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.repositories.accounts.BankAccountRepo;
import com.parker.personalfinanceapp.repositories.accounts.LoanAccountRepo;
import com.parker.personalfinanceapp.repositories.accounts.RetirementAccountRepo;
import com.parker.personalfinanceapp.repositories.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    BankAccountRepo bankAccountRepo;

    @Autowired
    LoanAccountRepo loanAccountRepo;

    @Autowired
    RetirementAccountRepo retirementAccountRepo;

    public Account getAccountType(String accountType) throws NoSuchAccountException {
        return AccountFactory.createAccount(accountType);
    }

    public Account getAccount(Long accountId, String accountType) throws NoSuchAccountException {
        switch(accountType) {
            case "BankAccount":
                Optional<BankAccount> bankAccountOptional = bankAccountRepo.findById(accountId);
                if (bankAccountOptional.isPresent()) {
                    return bankAccountOptional.get();
                } else {
                    throw new NoSuchAccountException("Bank account does not exist.");
                }
            case "LoanAccount":
                Optional<LoanAccount> loanAccountOptional = loanAccountRepo.findById(accountId);
                if (loanAccountOptional.isPresent()) {
                    return loanAccountOptional.get();
                } else {
                    throw new NoSuchAccountException("Loan account does not exist.");
                }
            case "RetirementAccount":
                Optional<RetirementAccount> retirementAccountOptional = retirementAccountRepo.findById(accountId);
                if (retirementAccountOptional.isPresent()) {
                    return retirementAccountOptional.get();
                } else {
                    throw new NoSuchAccountException("Retirement account does not exist.");
                }
            default:
                throw new NoSuchAccountException("Account does not exist.");
        }

    }

    public Account updateAccount(Account account, String accountType) throws NoSuchAccountException {
        switch(accountType) {
            case "BankAccount":
                BankAccount bankAccount = (BankAccount) account;
                BankAccount newBankAccount;
                Optional<BankAccount> bankAccountOptional = bankAccountRepo.findById(bankAccount.getId());
                if (bankAccountOptional.isPresent()) {
                    newBankAccount = bankAccountOptional.get();
                    newBankAccount.setAccountNum(bankAccount.getAccountNum());
                    newBankAccount.setBankAccountType(bankAccount.getBankAccountType());
                    newBankAccount.setBankName(bankAccount.getBankName());
                    newBankAccount.setStartBalance(bankAccount.getStartBalance());
                    newBankAccount.setCurrentBalance(bankAccount.getCurrentBalance());
                } else {
                    throw new NoSuchAccountException("Bank account does not exist.");
                }
                return bankAccountRepo.save(newBankAccount);
            case "LoanAccount":
                LoanAccount loanAccount = (LoanAccount) account;
                LoanAccount newLoanAccount;
                Optional<LoanAccount> loanAccountOptional = loanAccountRepo.findById(loanAccount.getId());
                if (loanAccountOptional.isPresent()) {
                    newLoanAccount = loanAccountOptional.get();
                    newLoanAccount.setAccountNum(loanAccount.getAccountNum());
                    newLoanAccount.setLoanAccountType(loanAccount.getLoanAccountType());
                    newLoanAccount.setCurrentBalance(loanAccount.getCurrentBalance());
                    newLoanAccount.setStartBalance(loanAccount.getStartBalance());
                    newLoanAccount.setLenderName(loanAccount.getLenderName());
                    newLoanAccount.setInterestRate(loanAccount.getInterestRate());
                    newLoanAccount.setMonthlyDueDate(loanAccount.getMonthlyDueDate());
                    newLoanAccount.setPaymentAmt(loanAccount.getPaymentAmt());
                } else {
                    throw new NoSuchAccountException("Loan account does not exist.");
                }
                return loanAccountRepo.save(newLoanAccount);
            case "RetirementAccount":
                RetirementAccount retirementAccount = (RetirementAccount) account;
                RetirementAccount newRetirementAccount;
                Optional<RetirementAccount> retirementAccountOptional =
                        retirementAccountRepo.findById(retirementAccount.getId());
                if (retirementAccountOptional.isPresent()) {
                    newRetirementAccount = retirementAccountOptional.get();
                    newRetirementAccount.setAccountNum(retirementAccount.getAccountNum());
                    newRetirementAccount.setCurrentBalance(retirementAccount.getCurrentBalance());
                    newRetirementAccount.setStartBalance(retirementAccount.getStartBalance());
                    newRetirementAccount.setFirmName(retirementAccount.getFirmName());
                } else {
                    throw new NoSuchAccountException("Retirement account does not exist.");
                }
                return retirementAccountRepo.save(newRetirementAccount);
            default:
                throw new NoSuchAccountException("Account does not exist.");
        }
    }

    public void deleteAccount(Account account, String accountType) throws NoSuchAccountException {
        switch(accountType) {
            case "BankAccount":
                Optional<BankAccount> bankAccountOptional = bankAccountRepo.findById(account.getId());
                if (bankAccountOptional.isPresent()) {
                    bankAccountRepo.delete(bankAccountOptional.get());
                } else {
                    throw new NoSuchAccountException("Bank Account does not exist.");
                }
            case "LoanAccount":
                Optional<LoanAccount> loanAccountOptional = loanAccountRepo.findById(account.getId());
                if (loanAccountOptional.isPresent()) {
                    loanAccountRepo.delete(loanAccountOptional.get());
                } else {
                    throw new NoSuchAccountException("Loan account does not exist.");
                }
            case "RetirementAccount":
                Optional<RetirementAccount> retirementAccountOptional = retirementAccountRepo.findById(account.getId());
                if (retirementAccountOptional.isPresent()) {
                    retirementAccountRepo.delete(retirementAccountOptional.get());
                } else {
                    throw new NoSuchAccountException("Retirement account does not exist.");
                }
            default:
                throw new NoSuchAccountException("Account does not exist.");
        }
    }

    @Transactional
    public Account createAccount(Long userId, String accountType, Account account) throws NoSuchAccountException, NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(userId);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            throw new NoSuchUserException("User does not exist.");
        }

        switch(accountType) {
            case "BankAccount":
                addUserAccount(user, account, accountType);
                return bankAccountRepo.save((BankAccount) account);
            case "LoanAccount":
                addUserAccount(user, account, accountType);
                return loanAccountRepo.save((LoanAccount) account);
            case "RetirementAccount":
                addUserAccount(user, account, accountType);
                return retirementAccountRepo.save((RetirementAccount) account);
            default:
                throw new NoSuchAccountException("Account does not exist.");
        }
    }

    private void addUserAccount(User user, Account account, String accountType) throws NoSuchAccountException {
        user.addAccount(account, accountType);
        userRepo.save(user);
    }
}
