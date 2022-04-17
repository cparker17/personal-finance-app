package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchReportException;
import com.parker.personalfinanceapp.models.reports.AccountsSummary;
import com.parker.personalfinanceapp.models.reports.BudgetActualReport;
import com.parker.personalfinanceapp.models.Report;
import com.parker.personalfinanceapp.models.reports.ExpenseSummary;
import com.parker.personalfinanceapp.models.reports.LoansSummary;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    public Report getReport(Long userId, String reportType) throws NoSuchReportException {
        switch (reportType) {
            case "BudgetActual": return getBudgetActualReport(userId);
            case "AccountsSummary": return getAccountsSummary(userId);
            case "LoansSummary": return getLoansSummary(userId);
            case "ExpenseSummary": return getExpenseSummary(userId);
            default: throw new NoSuchReportException("Report not found.");
        }
    }

    private BudgetActualReport getBudgetActualReport(Long userId) {
    }

    private AccountsSummary getAccountsSummary(Long userId) {
    }

    private LoansSummary getLoansSummary(Long userId) {

    }

    private ExpenseSummary getExpenseSummary(Long userId) {

    }
}
