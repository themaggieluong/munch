package com.techelevator.services;

import com.techelevator.dao.CategoryDao;
import com.techelevator.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> getCategories(){
        return categoryDao.getCategories();
    }
    @Override
    public Category getCategory(int categoryId){
        return categoryDao.getCategory(categoryId);
    }

    @Override
    public boolean addCategory(Category category){
        return categoryDao.addCategory(category);
    }
}
