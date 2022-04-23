package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.exceptions.NoSuchBudgetException;
import com.parker.personalfinanceapp.exceptions.NoSuchReportException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.UserFactory;
import com.parker.personalfinanceapp.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportsController {
    @Autowired
    ReportService reportService;

    @GetMapping("/{reportType}")
    private String viewReport(Model model, Authentication auth, @PathVariable(name="reportType") String reportType)
            throws NoSuchReportException, NoSuchUserException, NoSuchBudgetException {
        model.addAttribute("report",
                reportService.getReport(UserFactory.createUser(auth).getId(), reportType));
        switch(reportType) {
            case "BudgetActual": return "budget-actual-report";
            case "AccountsSummary":  return "accounts-summary";
            case "LoansSummary": return "loans-summary";
            case "ExpenseSummary": return "expense-summary";
            default: throw new NoSuchReportException("Report not found.");
        }
    }
}
