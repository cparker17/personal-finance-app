package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchBudgetException;
import com.parker.personalfinanceapp.exceptions.NoSuchReportException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.transactions.Deposit;
import com.parker.personalfinanceapp.models.transactions.Expense;
import com.parker.personalfinanceapp.models.transactions.LoanPayment;
import com.parker.personalfinanceapp.models.transactions.Withdrawal;
import com.parker.personalfinanceapp.models.budget.*;
import com.parker.personalfinanceapp.models.reports.AccountsSummary;
import com.parker.personalfinanceapp.models.reports.BudgetActualReport;
import com.parker.personalfinanceapp.models.reports.Report;
import com.parker.personalfinanceapp.models.reports.ExpenseSummary;
import com.parker.personalfinanceapp.models.reports.LoansSummary;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
import com.parker.personalfinanceapp.repositories.UserRepo;
import com.parker.personalfinanceapp.models.budget.BudgetFactory;
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

    private AccountsSummary getAccountsSummary(Long userId) throws NoSuchUserException {
        User user = UserFactory.getUser(userId);
        return AccountsSummary.builder()
                .bankAccounts(user.getBankAccounts())
                .loans(user.getLoans())
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
        User user = UserFactory.getUser(userId);
        return LoansSummary.builder()
                .loans(user.getLoans())
                .loanPayments(getUserLoanPayments(user))
                .build();
    }

    private List<LoanPayment> getUserLoanPayments(User user) {
        List<LoanPayment> loanPayments = new ArrayList<>();
        user.getLoans().forEach(loan -> loanPayments.addAll(loan.getLoanPayments()));
        return loanPayments;
    }

    private ExpenseSummary getExpenseSummary(Long userId) throws NoSuchBudgetException, NoSuchUserException {
        User user = UserFactory.getUser(userId);
        Budget budget = BudgetFactory.getBudgetFromDB(user.getBudget().getId());
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
