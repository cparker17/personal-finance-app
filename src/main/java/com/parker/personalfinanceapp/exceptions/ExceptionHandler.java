package com.parker.personalfinanceapp.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler extends Exception {
    public String handleReportException(NoSuchReportException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error-page";
    }

    public String handleAccountException(NoSuchAccountException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error-page";
    }

    public String handleUserException(NoSuchUserException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error-page";
    }

    public String handleBudgetException(NoSuchBudgetException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error-page";
    }

    public String handleRetirementPlanException(NoSuchRetirementPlanException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error-page";
    }

    public String handleGoalException(NoSuchGoalException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error-page";
    }

    public String handleTransactionException(NoSuchTransactionException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error-page";
    }
}
