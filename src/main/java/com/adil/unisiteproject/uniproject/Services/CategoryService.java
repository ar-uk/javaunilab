package com.adil.unisiteproject.uniproject.Services;

import com.adil.unisiteproject.uniproject.Repository.CategoryRepository;
import com.adil.unisiteproject.uniproject.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Optionally, you can create a method to get a category by its ID
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    // You can also add a method to create or save a category if needed
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}