package com.parker.personalfinanceapp.repositories;

import com.parker.personalfinanceapp.models.RetirementPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetirementPlanRepo extends JpaRepository<RetirementPlan, Long> {
}
