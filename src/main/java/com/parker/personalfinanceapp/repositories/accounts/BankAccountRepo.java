package com.parker.personalfinanceapp.repositories.accounts;

import com.parker.personalfinanceapp.models.accounts.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepo extends JpaRepository<BankAccount, Long> {
}
