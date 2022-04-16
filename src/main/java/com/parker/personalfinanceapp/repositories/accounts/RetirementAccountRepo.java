package com.parker.personalfinanceapp.repositories.accounts;

import com.parker.personalfinanceapp.models.accounts.RetirementAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetirementAccountRepo extends JpaRepository<RetirementAccount, Long> {
}
