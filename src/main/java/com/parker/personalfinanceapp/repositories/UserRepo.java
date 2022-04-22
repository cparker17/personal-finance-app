package com.parker.personalfinanceapp.repositories;

import com.parker.personalfinanceapp.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findDistinctByEmail(String email);
}
