package com.parker.personalfinanceapp.models.accounts;

import com.parker.personalfinanceapp.models.enumerations.BankAccountType;
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
public class BankAccount implements Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BankAccountType bankAccountType;

    private String bankName;

    private Long accountNum;

    private BigDecimal startBalance;

    private BigDecimal currentBalance;
}
