package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchBudgetException;
import com.parker.personalfinanceapp.exceptions.NoSuchReportException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.exceptions.PersonalFinanceAppException;
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
        List<Expense> expenses = new ArrayList<>();
        budget.getCategories().forEach(category -> expenses.addAll(category.getExpenses()));
        return BudgetActualReport.builder()
                .budget(budget)
                .expenses(expenses)
                .build();
    }

    private AccountsSummary getAccountsSummary(Long userId) throws NoSuchUserException {
        User user = getUser(userId);
        return AccountsSummary.builder()
                .bankAccounts(user.getBankAccounts())
                .loans(user.getLoanAccounts())
                .retirementAccounts(user.getRetirementAccounts())
                .deposits(getUserDeposits(user))
                .withdrawals(getUserWithdrawals(user))
                .build();
    }

    private List<Deposit> getUserDeposits(User user) {
        List<Deposit> deposits = new ArrayList<>();
        user.getBankAccounts().forEach(bankAccount -> deposits.addAll(bankAccount.getDeposits()));
        user.getRetirementAccounts().forEach(retirementAccount -> deposits.addAll(retirementAccount.getDeposits()));
        return deposits;
    }

    private List<Withdrawal> getUserWithdrawals(User user) {
        List<Withdrawal> withdrawals = new ArrayList<>();
        user.getBankAccounts().forEach(bankAccount -> withdrawals.addAll(bankAccount.getWithdrawals()));
        return withdrawals;
    }

    private LoansSummary getLoansSummary(Long userId) throws NoSuchUserException {
        User user = getUser(userId);
        return LoansSummary.builder()
                .loans(user.getLoanAccounts())
                .loanPayments(getUserLoanPayments(user))
                .build();
    }

    private List<LoanPayment> getUserLoanPayments(User user) {
        List<LoanPayment> loanPayments = new ArrayList<>();
        user.getLoanAccounts().forEach(loan -> loanPayments.addAll(loan.getLoanPayments()));
        return loanPayments;
    }

    private ExpenseSummary getExpenseSummary(Long userId) throws NoSuchBudgetException, NoSuchUserException {
        User user = getUser(userId);
        Budget budget = budgetService.getBudget(user);
        return ExpenseSummary.builder()
                .categories(budget.getCategories())
                .expenses(getUserExpenses(budget))
                .build();
    }

    private List<Expense> getUserExpenses(Budget budget) {
        List<Expense> expenses = new ArrayList<>();
        budget.getCategories().forEach(category -> expenses.addAll(category.getExpenses()));
        return expenses;
    }
}
