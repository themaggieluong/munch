package com.techelevator.model;

import java.util.Objects;

public class Category {
    private int categoryId;
    private String categoryName;

    public Category(){

    }

    public Category(int category_id, String category_name) {
        this.categoryId = category_id;
        this.categoryName = category_name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "category_id=" + categoryId +
                ", category_name='" + categoryName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return categoryId == category.categoryId && Objects.equals(categoryName, category.categoryName);
    }
}
