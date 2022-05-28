package com.parker.personalfinanceapp.repositories;

import com.parker.personalfinanceapp.models.LoanAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepo extends JpaRepository<LoanAccount, Long> {
}
