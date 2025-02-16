package com.techelevator.services;

import com.techelevator.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
    Category getCategory(int categoryId);

    boolean addCategory(Category category);
}
