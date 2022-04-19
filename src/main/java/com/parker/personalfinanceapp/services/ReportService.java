package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchBudgetException;
import com.parker.personalfinanceapp.exceptions.NoSuchReportException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.budget.*;
import com.parker.personalfinanceapp.models.reports.AccountsSummary;
import com.parker.personalfinanceapp.models.reports.BudgetActualReport;
import com.parker.personalfinanceapp.models.reports.Report;
import com.parker.personalfinanceapp.models.reports.ExpenseSummary;
import com.parker.personalfinanceapp.models.reports.LoansSummary;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
import com.parker.personalfinanceapp.repositories.user.UserRepo;
import com.parker.personalfinanceapp.services.budget.BudgetFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    UserRepo userRepo;

    public Report getReport(Long userId, String reportType) throws NoSuchReportException, NoSuchUserException, NoSuchBudgetException {
        switch (reportType) {
            case "BudgetActual": return getBudgetActualReport(userId);
            case "AccountsSummary": return getAccountsSummary(userId);
            case "LoansSummary": return getLoansSummary(userId);
            case "ExpenseSummary": return getExpenseSummary(userId);
            default: throw new NoSuchReportException("Report not found.");
        }
    }

    private BudgetActualReport getBudgetActualReport(Long userId) throws NoSuchUserException {
        Budget budget = UserFactory.getUser(userId).getBudget();
        List<Expense> expenses = new ArrayList<>();
        budget.getCategories().forEach(category -> expenses.addAll(category.getExpenses()));
        return BudgetActualReport.builder()
                .budget(budget)
                .expenses(expenses)
                .build();
    }

    private AccountsSummary getAccountsSummary(Long userId) throws NoSuchUserException, NoSuchBudgetException {
        User user = UserFactory.getUser(userId);
        Budget budget = BudgetFactory.getBudget(user.getBudget().getId());
        return AccountsSummary.builder()
                .bankAccounts(user.getBankAccounts())
                .loanAccounts(user.getLoanAccounts())
                .retirementAccounts(user.getRetirementAccounts())
                .deposits(getUserDeposits(budget))
                .withdrawals(getUserWithdrawals(budget))
                .build();
    }

    private List<Deposit> getUserDeposits(Budget budget) throws NoSuchBudgetException {
        List<Deposit> deposits = new ArrayList<>();
        budget.getCategories().forEach(category -> deposits.addAll(category.getDeposits()));
        return deposits;
    }

    private List<Withdrawal> getUserWithdrawals(Budget budget) {
        List<Withdrawal> withdrawals = new ArrayList<>();
        budget.getCategories().forEach(category -> withdrawals.addAll(category.getWithdrawals()));
        return withdrawals;
    }

    private LoansSummary getLoansSummary(Long userId) throws NoSuchUserException, NoSuchBudgetException {
        User user = UserFactory.getUser(userId);
        Budget budget = BudgetFactory.getBudget(user.getBudget().getId());
        return LoansSummary.builder()
                .loanAccounts(user.getLoanAccounts())
                .loanPayments(getUserLoanPayments(budget))
                .build();
    }

    private List<LoanPayment> getUserLoanPayments(Budget budget) {
        List<LoanPayment> loanPayments = new ArrayList<>();
        budget.getCategories().forEach(category -> loanPayments.addAll(category.getLoanPayments()));
        return loanPayments;
    }

    private ExpenseSummary getExpenseSummary(Long userId) throws NoSuchBudgetException, NoSuchUserException {
        User user = UserFactory.getUser(userId);
        Budget budget = BudgetFactory.getBudget(user.getBudget().getId());
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
