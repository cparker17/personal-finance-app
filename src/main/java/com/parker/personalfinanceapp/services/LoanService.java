package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.Account;
import com.parker.personalfinanceapp.models.LoanAccount;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.repositories.LoanRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    LoanRepo loanRepo;

    public LoanAccount getLoan(Long loanId) throws NoSuchAccountException {
        return getLoanFromDB(loanId);
    }

    public LoanAccount updateLoan(LoanAccount newLoan) throws NoSuchAccountException {
        LoanAccount loan = getLoanFromDB(newLoan.getId());
        loan.setAccountType(newLoan.getAccountType());
        loan.setInstitutionName(newLoan.getInstitutionName());
        loan.setAccountNum(newLoan.getAccountNum());
        loan.setInterestRate(newLoan.getInterestRate());
        loan.setStartBalance(newLoan.getStartBalance());
        loan.setCurrentBalance(newLoan.getCurrentBalance());
        loan.setMonthlyDueDate(newLoan.getMonthlyDueDate());
        loan.setPaymentAmt(newLoan.getPaymentAmt());
        return loanRepo.save(loan);
    }

    public void deleteLoan(LoanAccount loan) throws NoSuchAccountException {
        loanRepo.delete(getLoanFromDB(loan.getId()));
    }

    public LoanAccount createLoan(User user, LoanAccount loan) {
        addLoanToUser(user, loan);
        return loanRepo.save(loan);
    }

    private void addLoanToUser(User user, LoanAccount loan) {
        user.addLoan(loan);
        userRepo.save(user);
    }

    private LoanAccount getLoanFromDB(Long loanId) throws NoSuchAccountException {
        Optional<LoanAccount> loanOptional = loanRepo.findById(loanId);
        if (loanOptional.isPresent()) {
            return loanOptional.get();
        } else {
            throw new NoSuchAccountException("Loan account does not exist.");
        }
    }

    public List<Account> getAllLoans(Long userId) throws NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get().getLoanAccounts();
        } else {
            throw new NoSuchUserException("User does not exist.");
        }
    }
}
