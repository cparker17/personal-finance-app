package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.Loan;
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

    public Loan getLoan(Long loanId) throws NoSuchAccountException {
        return getLoanFromDB(loanId);
    }

    public Loan updateLoan(Loan newLoan) throws NoSuchAccountException {
        Loan loan = getLoanFromDB(newLoan.getId());
        loan.setAccountType(newLoan.getAccountType());
        loan.setLenderName(newLoan.getLenderName());
        loan.setAccountNum(newLoan.getAccountNum());
        loan.setInterestRate(newLoan.getInterestRate());
        loan.setStartBalance(newLoan.getStartBalance());
        loan.setCurrentBalance(newLoan.getCurrentBalance());
        loan.setMonthlyDueDate(newLoan.getMonthlyDueDate());
        loan.setPaymentAmt(newLoan.getPaymentAmt());
        return loanRepo.save(loan);
    }

    public void deleteLoan(Loan loan) throws NoSuchAccountException {
        loanRepo.delete(getLoanFromDB(loan.getId()));
    }

    public Loan createLoan(User user, Loan loan) {
        addLoanToUser(user, loan);
        return loanRepo.save(loan);
    }

    private void addLoanToUser(User user, Loan loan) {
        user.addLoan(loan);
        userRepo.save(user);
    }

    private Loan getLoanFromDB(Long loanId) throws NoSuchAccountException {
        Optional<Loan> loanOptional = loanRepo.findById(loanId);
        if (loanOptional.isPresent()) {
            return loanOptional.get();
        } else {
            throw new NoSuchAccountException("Loan account does not exist.");
        }
    }

    public List<Loan> getAllLoans(Long userId) throws NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get().getLoans();
        } else {
            throw new NoSuchUserException("User does not exist.");
        }
    }
}
