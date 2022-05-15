package com.parker.personalfinanceapp;

import com.parker.personalfinanceapp.models.*;
import com.parker.personalfinanceapp.repositories.BudgetRepo;
import com.parker.personalfinanceapp.repositories.CategoryRepo;
import com.parker.personalfinanceapp.repositories.RoleRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.parker.personalfinanceapp.models.Role.Roles.ROLE_ADMIN;
import static com.parker.personalfinanceapp.models.Role.Roles.ROLE_USER;

@SpringBootApplication
@EntityScan("com.parker.personalfinanceapp.models")
public class PersonalFinanceAppApplication {
    @Autowired
    RoleRepo roleRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    BudgetRepo budgetRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(PersonalFinanceAppApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner loadInitialData() {
        return (args) -> {
            if (roleRepo.findAll().isEmpty()) {
                Role USER = new Role(ROLE_USER);
                roleRepo.save(USER);
                Role ADMIN = new Role(ROLE_ADMIN);
                roleRepo.save(ADMIN);
            }

            if (userRepo.findAll().isEmpty()) {
                List<Category> categoryList = new ArrayList<>();
                for (int i = 1; i <= 10; i++) {
                    categoryList.add(categoryRepo.save(Category.builder()
                            .categoryType(CategoryType.NEEDS)
                            .monthlyBudgetAmt(BigDecimal.valueOf(100.00))
                            .name("Test Category: " + i)
                            .build()));
                }

                User testUser = User.builder()
                        .username("test")
                        .password(passwordEncoder.encode("test"))
                        .firstName("test")
                        .lastName("test")
                        .address(Address.builder()
                                .streetAddress("address")
                                .city("city")
                                .state("state")
                                .zip("zip")
                                .build())
                        .email("email@email.com")
                        .role(roleRepo.findRoleById(1L))
                        .budget(budgetRepo.save(Budget.builder()
                                .categories(categoryList)
                                .monthlyIncome(BigDecimal.valueOf(10000.00)).build()))
                        .build();
                userRepo.save(testUser);
            }
        };
    }
}
