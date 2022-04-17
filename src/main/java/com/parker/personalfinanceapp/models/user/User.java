package com.parker.personalfinanceapp.models.user;

import com.parker.personalfinanceapp.exceptions.NoSuchAccountException;
import com.parker.personalfinanceapp.models.Goal;
import com.parker.personalfinanceapp.models.accounts.Account;
import com.parker.personalfinanceapp.models.accounts.BankAccount;
import com.parker.personalfinanceapp.models.accounts.LoanAccount;
import com.parker.personalfinanceapp.models.accounts.RetirementAccount;
import com.parker.personalfinanceapp.models.budget.Budget;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "Username required.")
    @Column(unique = true)
    private String username;

    @NotNull
    @NotBlank(message = "Password required.")
    private String password;

    @NotNull
    @NotBlank(message = "First name required.")
    private String firstName;

    @NotNull
    @NotBlank(message = "Last name required.")
    private String lastName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_address_id")
    private Address address;

    @Column(unique = true)
    @NotBlank(message = "Email required.")
    @Email
    private String email;

    private String phone;

    private LocalDate birthDate;

    @OneToOne
    private Budget budget;

    @OneToOne
    private Goal goal;

    @OneToMany
    private List<BankAccount> bankAccounts;

    @OneToMany
    List<LoanAccount> loanAccounts;

    @OneToMany
    List<RetirementAccount> retirementAccounts;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Role role;

    @Column(nullable = false)
    private boolean isAccountNonExpired = true;

    @Column(nullable = false)
    private boolean isAccountNonLocked = true;

    @Column(nullable = false)
    private boolean isCredentialsNonExpired = true;

    @Column(nullable = false)
    private boolean isEnabled = true;

    public void addAccount(Account account, String accountType) throws NoSuchAccountException {
        switch(accountType) {
            case "BankAccount":
                if (bankAccounts == null) {
                    bankAccounts = new ArrayList<>();
                } else {
                    bankAccounts.add((BankAccount) account);
                }
            case "LoanAccount":
                if (loanAccounts == null) {
                    loanAccounts = new ArrayList<>();
                } else {
                    loanAccounts.add((LoanAccount) account);
                }
            case "RetirementAccount":
                if (retirementAccounts == null) {
                    retirementAccounts = new ArrayList<>();
                } else {
                    retirementAccounts.add((RetirementAccount) account);
                }
            default:
                throw new NoSuchAccountException("Account does not exist.");
        }
    }
}
