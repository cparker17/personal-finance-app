package com.parker.personalfinanceapp.repositories.budget;

import com.parker.personalfinanceapp.models.budget.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepo extends JpaRepository<Deposit, Long> {
}
