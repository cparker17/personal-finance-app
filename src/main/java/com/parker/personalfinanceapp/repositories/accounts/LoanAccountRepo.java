package com.parker.personalfinanceapp.repositories.accounts;

import com.parker.personalfinanceapp.models.accounts.LoanAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanAccountRepo extends JpaRepository<LoanAccount, Long> {
}
