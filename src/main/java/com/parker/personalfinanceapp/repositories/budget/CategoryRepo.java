package com.parker.personalfinanceapp.repositories.budget;

import com.parker.personalfinanceapp.models.budget.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
}