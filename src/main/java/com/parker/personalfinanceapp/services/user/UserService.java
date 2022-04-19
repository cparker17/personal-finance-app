package com.parker.personalfinanceapp.services.user;

import com.parker.personalfinanceapp.exceptions.NoSuchUserException;
import com.parker.personalfinanceapp.models.user.User;
import com.parker.personalfinanceapp.models.user.UserFactory;
import com.parker.personalfinanceapp.repositories.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public User getUserInfo(Long userId) throws NoSuchUserException {
        return UserFactory.getUser(userId);
    }

    public User updateUserInfo(Long userId, User newUser) throws NoSuchUserException {
        User user = UserFactory.getUser(userId);
        user.setAddress(newUser.getAddress());
        user.setBirthDate(newUser.getBirthDate());
        user.setEmail(newUser.getEmail());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setPhone(newUser.getPhone());
        return userRepo.save(user);
    }
}
