package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.dto.*;
import com.parker.personalfinanceapp.exceptions.NoSuchBudgetException;
import com.parker.personalfinanceapp.exceptions.NoSuchReportException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.*;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    BudgetService budgetService;

    public Report getReport(Long userId, String reportType) throws NoSuchReportException, NoSuchUserException, NoSuchBudgetException {
        switch (reportType) {
            case "BudgetActual": return getBudgetActualReport(userId);
            case "AccountsSummary": return getAccountsSummary(userId);
            case "LoansSummary": return getLoansSummary(userId);
            case "ExpenseSummary": return getExpenseSummary(userId);
            default: throw new NoSuchReportException("Report not found.");
        }
    }

    private User getUser(Long userId) throws NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new NoSuchUserException("User does not exist.");
        }
    }

    private BudgetActualReport getBudgetActualReport(Long userId) throws NoSuchUserException {
        Budget budget = getUser(userId).getBudget();
        List<Transaction> expenses = new ArrayList<>();
        //budget.getCategories().forEach(category -> expenses.addAll(category.getTransactions()));
        return BudgetActualReport.builder()
                .budget(budget)
                .expenses(expenses)
                .build();
    }

    private AccountsSummary getAccountsSummary(Long userId) throws NoSuchUserException {
        User user = getUser(userId);
        return AccountsSummary.builder()
                .accounts(user.getAccounts())
                .deposits(getUserDeposits(user))
                .withdrawals(getUserWithdrawals(user))
                .build();
    }

    private List<Transaction> getUserDeposits(User user) {
        List<Transaction> deposits = new ArrayList<>();
        user.getAccounts().forEach(bankAccount -> deposits.addAll(bankAccount.getDeposits()));
        user.getAccounts().forEach(retirementAccount -> deposits.addAll(retirementAccount.getDeposits()));
        return deposits;
    }

    private List<Transaction> getUserWithdrawals(User user) {
        List<Transaction> withdrawals = new ArrayList<>();
        user.getAccounts().forEach(bankAccount -> withdrawals.addAll(bankAccount.getWithdrawals()));
        return withdrawals;
    }

    private LoansSummary getLoansSummary(Long userId) throws NoSuchUserException {
        User user = getUser(userId);
        return LoansSummary.builder()
                .loans(user.getLoanAccounts())
                .loanPayments(getUserLoanPayments(user))
                .build();
    }

    private List<Transaction> getUserLoanPayments(User user) {
        List<Transaction> loanPayments = new ArrayList<>();
        user.getLoanAccounts().forEach(loan -> loanPayments.addAll(loan.getLoanPayments()));
        return loanPayments;
    }

    private ExpenseSummary getExpenseSummary(Long userId) throws NoSuchBudgetException, NoSuchUserException {
        Budget budget = budgetService.getBudget(userId);
        return ExpenseSummary.builder()
                .categories(budget.getCategories())
                .expenses(getUserExpenses(budget))
                .build();
    }

    private List<Transaction> getUserExpenses(Budget budget) {
        List<Transaction> expenses = new ArrayList<>();
        //budget.getCategories().forEach(category -> expenses.addAll(category.getTransactions()));
        return expenses;
    }
}
