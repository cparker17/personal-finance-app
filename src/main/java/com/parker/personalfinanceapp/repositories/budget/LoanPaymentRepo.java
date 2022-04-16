package com.parker.personalfinanceapp.repositories.budget;

import com.parker.personalfinanceapp.models.budget.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanPaymentRepo extends JpaRepository<LoanPayment, Long> {
}
