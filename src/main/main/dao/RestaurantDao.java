package com.techelevator.dao;

import com.techelevator.model.Restaurant;
import com.techelevator.model.RestaurantCategoryDto;
import com.techelevator.model.UserRestaurantDto;

import java.util.List;

public interface RestaurantDao {

    List<Restaurant> getRestaurants();

    Restaurant getRestaurantById(int restaurantId);

    Restaurant addRestaurant(Restaurant restaurant);

    Restaurant updateRestaurant(Restaurant restaurant);

    List<Restaurant> getRestaurantsByCategory(int categoryId, int userId);

    RestaurantCategoryDto addRestaurantByCategory(int restaurantId, int categoryId);

    UserRestaurantDto addUserRestaurant(UserRestaurantDto userRestaurantDto);

    List<Restaurant> getUsersLikedRestaurants(int userId);

    List<Restaurant> getUsersRejectedRestaurants(int userId);

    boolean updateUserRestaurant(UserRestaurantDto userRestaurantDto);

    List<Restaurant> getNotLikedOrRejectedRestaurants(int userId);

}
