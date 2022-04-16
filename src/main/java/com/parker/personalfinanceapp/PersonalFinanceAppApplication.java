package com.parker.personalfinanceapp;

import com.parker.personalfinanceapp.models.Address;
import com.parker.personalfinanceapp.models.Role;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.repositories.RoleRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.parker.personalfinanceapp.models.Role.Roles.ROLE_ADMIN;
import static com.parker.personalfinanceapp.models.Role.Roles.ROLE_USER;

@SpringBootApplication
public class PersonalFinanceAppApplication {
    @Autowired
    RoleRepo roleRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(PersonalFinanceAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadInitialData() {
        return (args) -> {
            if (roleRepo.findAll().isEmpty()) {
                Role USER = new Role(ROLE_USER);
                roleRepo.save(USER);
                Role ADMIN = new Role(ROLE_ADMIN);
                roleRepo.save(ADMIN);
            }

            if (userRepo.findAll().isEmpty()) {
                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .firstName("admin")
                        .lastName("admin")
                        .address(Address.builder()
                                .streetAddress("3206 Patapsco Rd")
                                .city("Finksburg")
                                .state("MD")
                                .zip("21048")
                                .build())
                        .email("rk21048@gmail.com")
                        .role(roleRepo.findRoleById(2L))
                        .build();
                userRepo.save(admin);
            }
        };
    }
}
