package com.parker.personalfinanceapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionTypeWrapper {
    private String type;

    public TransactionTypeWrapper(String transactionType) {
        switch (transactionType) {
            case "deposit":
                this.type = "Deposits";
                break;
            case "expense":
                this.type = "Expenses";
                break;
            case "withdrawal":
                this.type = "Withdrawals";
                break;
            default:
                this.type = "Loan Payments";

        }
    }
}
