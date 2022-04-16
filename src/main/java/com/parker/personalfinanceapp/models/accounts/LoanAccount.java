package com.parker.personalfinanceapp.models.accounts;

import com.parker.personalfinanceapp.models.enumerations.LoanAccountType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanAccount implements Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LoanAccountType loanAccountType;

    private String lenderName;

    private Long accountNum;

    private Double interestRate;

    private BigDecimal startBalance;

    private BigDecimal currentBalance;

    private Integer monthlyDueDate;

    private BigDecimal paymentAmt;
}
