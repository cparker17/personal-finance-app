package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.models.UserFactory;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public User getUserInfo(User user) throws NoSuchUserException {
        return UserFactory.getUser(user.getId());
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
}
