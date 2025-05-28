package com.example.shopapp.Service;

import com.example.shopapp.Dtos.CategoryDTO;
import com.example.shopapp.Model.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category updateCategory(Long id, CategoryDTO category);
    void deleteCategoryById(Long id);
}
