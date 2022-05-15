package com.parker.personalfinanceapp.services;

import com.parker.personalfinanceapp.exceptions.NoSuchCategoryException;
import com.parker.personalfinanceapp.models.Category;
import com.parker.personalfinanceapp.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    public void updateCategory(Category category) {
        categoryRepo.save(category);
    }

    public Category getCategory(Long id) throws NoSuchCategoryException {
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        if (categoryOptional.isPresent()) {
            return categoryOptional.get();
        } else {
            throw new NoSuchCategoryException("Category does not exist.");
        }
    }
}