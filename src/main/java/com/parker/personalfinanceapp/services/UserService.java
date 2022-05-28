package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.DuplicateUserException;
import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.Budget;
import com.parker.personalfinanceapp.models.Role;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.models.UserFactory;
import com.parker.personalfinanceapp.repositories.BudgetRepo;
import com.parker.personalfinanceapp.repositories.RoleRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    BudgetRepo budgetRepo;

    public User getUserInfo(User user) throws NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(user.getId());
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new NoSuchUserException("User does not exist.");
        }
    }

    public User updateUserInfo(User user, User newUser) {
        user.setAddress(newUser.getAddress());
        user.setBirthDate(newUser.getBirthDate());
        user.setEmail(newUser.getEmail());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setPhone(newUser.getPhone());
        return userRepo.save(user);
    }

    @Transactional
    public User registerAccount(User user) throws DuplicateUserException {
        if (userRepo.findDistinctByEmail(user.getEmail()) != null) {
            throw new DuplicateUserException("This customer already exists in the system.");
        }
        Role role = roleRepo.findRoleById(1L);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Budget budget = new Budget();
        budgetRepo.save(budget);
        user.setBudget(budget);
        return userRepo.save(user);
    }
}
