package com.parker.personalfinanceapp.repositories.budget;

import com.parker.personalfinanceapp.models.budget.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalRepo extends JpaRepository<Withdrawal, Long> {
}
