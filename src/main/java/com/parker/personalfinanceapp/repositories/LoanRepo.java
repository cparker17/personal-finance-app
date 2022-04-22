package com.parker.personalfinanceapp.repositories;

import com.parker.personalfinanceapp.models.accounts.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepo extends JpaRepository<Loan, Long> {
}
