package com.parker.personalfinanceapp.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler extends Exception {
    public String handleReportException(PersonalFinanceAppException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error-page";
    }
}
