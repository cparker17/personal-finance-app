package com.parker.personalfinanceapp.dto;

import com.parker.personalfinanceapp.models.Category;
import com.parker.personalfinanceapp.models.CategoryType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BaseCategoryData {
    private List<String> needsList;
    private List<String> wantsList;
    private List<String> savingsList;
    private List<Category> categoryList = new ArrayList<>();

    public BaseCategoryData() {
        this.needsList = List.of("Mortgage", "DayCare", "Life Insurance", "Gym Membership", "Auto Loan",
                "Auto Insurance", "Utilities", "Cell Phone", "Internet / TV");
        this.wantsList = List.of("Groceries", "Alcohol", "Haircut", "Medical", "Pet", "Entertainment", "Eating Out",
                "Vacations");
        this.savingsList = List.of("General Savings", "College Fund", "Retirement");

        for (String name : needsList) {
            categoryList.add(Category.builder()
                    .categoryType(CategoryType.NEEDS)
                    .name(name)
                    .monthlyBudgetAmt(BigDecimal.ZERO)
                    .build());
        }
        for (String name : wantsList) {
            categoryList.add(Category.builder()
                    .categoryType(CategoryType.WANTS)
                    .name(name)
                    .monthlyBudgetAmt(BigDecimal.ZERO)
                    .build());
        }
        for (String name : savingsList) {
            categoryList.add(Category.builder()
                    .categoryType(CategoryType.SAVINGS)
                    .name(name)
                    .monthlyBudgetAmt(BigDecimal.ZERO)
                    .build());
        }
    }
}
