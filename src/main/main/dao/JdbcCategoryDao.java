package com.techelevator.dao;

import com.techelevator.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCategoryDao implements CategoryDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcCategoryDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Category> getCategories(){
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * from category";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            Category category = mapRowToCategory(results);
            categories.add(category);
        }
        return categories;
    }

    @Override
    public Category getCategory(int categoryId){
        String sql = "SELECT * FROM category WHERE category_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, categoryId);
        if(results.next()){
            return mapRowToCategory(results);
        }
        else{
            return null;
        }
    }

    @Override
    public boolean addCategory(Category category){
        String sql = "INSERT into category (category_name) VALUES(?)";
        return jdbcTemplate.update(sql, category.getCategoryName()) == 1;
    }


    private Category mapRowToCategory(SqlRowSet rowSet){
        Category category = new Category();
        category.setCategoryId(rowSet.getInt("category_id"));
        category.setCategoryName(rowSet.getString("category_name"));
        return category;
    }
}
