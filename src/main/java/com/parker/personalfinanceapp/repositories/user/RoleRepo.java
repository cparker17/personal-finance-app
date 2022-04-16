package com.parker.personalfinanceapp.repositories.user;

import com.parker.personalfinanceapp.models.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findRoleById(Long id);
}
