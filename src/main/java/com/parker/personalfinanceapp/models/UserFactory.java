package com.parker.personalfinanceapp.models;

import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UserFactory {
    @Autowired
    static UserRepo userRepo;

    public static User createUser(Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return securityUser.getUser();
    }

    public static User getUser(Long userId) throws NoSuchUserException {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new NoSuchUserException("User does not exist.");
        }
    }
}
