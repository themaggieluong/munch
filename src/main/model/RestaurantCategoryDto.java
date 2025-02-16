package com.techelevator.model;

public class RestaurantCategoryDto {
    private int restaurantId;
    private int categoryId;
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "RestaurantCategoryDto{" +
                "restaurantId=" + restaurantId +
                ", categoryId=" + categoryId +
                '}';
    }
}
