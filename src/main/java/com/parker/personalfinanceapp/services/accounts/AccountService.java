package com.parker.personalfinanceapp.services.accounts;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.models.accounts.Account;
import com.parker.personalfinanceapp.models.accounts.BankAccount;
import com.parker.personalfinanceapp.models.accounts.LoanAccount;
import com.parker.personalfinanceapp.models.accounts.RetirementAccount;
import com.parker.personalfinanceapp.repositories.RetirementPlanRepo;
import com.parker.personalfinanceapp.repositories.accounts.BankAccountRepo;
import com.parker.personalfinanceapp.repositories.accounts.LoanAccountRepo;
import com.parker.personalfinanceapp.repositories.accounts.RetirementAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    BankAccountRepo bankAccountRepo;

    @Autowired
    LoanAccountRepo loanAccountRepo;

    @Autowired
    RetirementAccountRepo retirementAccountRepo;

    public Account getAccountType(String accountType) throws NoSuchAccountException {
        AccountFactory.createAccount(accountType);
    }

    public Account getAccount(Long userId, Long accountId) {

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

    public void deleteAccount(Account account) {
    }

    public Account createAccount(Long userId, String accountType) throws NoSuchAccountException {
        Account account = AccountFactory.createAccount(accountType);
    }
}
