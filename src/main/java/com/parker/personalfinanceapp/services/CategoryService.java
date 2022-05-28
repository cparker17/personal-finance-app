package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchCategoryException;
import com.parker.personalfinanceapp.models.Category;
import com.parker.personalfinanceapp.models.CategoryType;
import com.parker.personalfinanceapp.models.User;
import com.parker.personalfinanceapp.repositories.CategoryRepo;
import com.parker.personalfinanceapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    UserRepo userRepo;

    public void updateCategory(Category updatedCategory) {
        Category category = new Category();
        Optional<Category> categoryOptional = categoryRepo.findById(updatedCategory.getId());
        if (categoryOptional.isPresent()) {
            category = categoryOptional.get();
        }
        category.setName(updatedCategory.getName());
        category.setMonthlyBudgetAmt(updatedCategory.getMonthlyBudgetAmt());
        category.setCategoryType(getCategoryType(updatedCategory.getType()));
        categoryRepo.save(category);
    }

    private CategoryType getCategoryType(String type) {
        switch(type) {
            case "NEEDS": return CategoryType.NEEDS;
            case "WANTS": return CategoryType.WANTS;
            default: return CategoryType.SAVINGS;
        }
    }

    public Category getCategory(Long id) throws NoSuchCategoryException {
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        if (categoryOptional.isPresent()) {
            return categoryOptional.get();
        } else {
            throw new NoSuchCategoryException("Category does not exist.");
        }
    }

    public void createCategory(Long userId, Category category) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            category.setCategoryType(getCategoryType(category.getType()));
            user.getBudget().getCategories().add(category);
            categoryRepo.save(category);
            userRepo.save(user);
        }
    }
}