package com.parker.personalfinanceapp.controllers;

import com.parker.personalfinanceapp.services.RetirementPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RetirementPlanController {
    @Autowired
    RetirementPlanService retirementPlanService;


}
