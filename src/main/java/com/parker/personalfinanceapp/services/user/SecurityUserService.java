package com.parker.personalfinanceapp.services.user;

import com.parker.personalfinanceapp.models.user.SecurityUser;
import com.parker.personalfinanceapp.repositories.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecurityUser(userRepo.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found.")));
    }
}
