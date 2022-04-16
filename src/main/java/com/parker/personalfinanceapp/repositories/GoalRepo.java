package com.parker.personalfinanceapp.repositories;

import com.parker.personalfinanceapp.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepo extends JpaRepository<Goal, Long> {
}
