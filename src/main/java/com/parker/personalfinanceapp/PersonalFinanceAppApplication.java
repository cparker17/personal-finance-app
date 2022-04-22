package com.parker.personalfinanceapp;

import com.parker.personalfinanceapp.models.user.Address;
import com.parker.personalfinanceapp.models.user.Role;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.repositories.RoleRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.parker.personalfinanceapp.models.user.Role.Roles.ROLE_ADMIN;
import static com.parker.personalfinanceapp.models.user.Role.Roles.ROLE_USER;

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
                                .streetAddress("address")
                                .city("city")
                                .state("state")
                                .zip("zip")
                                .build())
                        .email("email@email.com")
                        .role(roleRepo.findRoleById(2L))
                        .build();
                userRepo.save(admin);
            }
        };
    }
}
