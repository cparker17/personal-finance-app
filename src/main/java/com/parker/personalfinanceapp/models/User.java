package com.parker.personalfinanceapp.models;

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
    private List<Account> accounts = new ArrayList<>();

    @OneToMany
    List<Loan> loanAccounts = new ArrayList<>();

    @OneToOne
    RetirementPlan retirementPlan;

    @OneToMany
    List<Transaction> transactions;

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

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void addLoan(Loan loan) {
        loanAccounts.add(loan);
    }
}
