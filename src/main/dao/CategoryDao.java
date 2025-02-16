package com.techelevator.dao;

import com.techelevator.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getCategories();
    Category getCategory(int categoryId);

    boolean addCategory(Category category);

}
