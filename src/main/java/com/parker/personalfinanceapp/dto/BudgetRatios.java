package com.parker.personalfinanceapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetRatios {
    private int needsRatio;
    private int wantsRatio;
    private int savingsRatio;
    private boolean isBalanced;

    public void setIsBalanced() {
        isBalanced = needsRatio == 50;
    }
}
