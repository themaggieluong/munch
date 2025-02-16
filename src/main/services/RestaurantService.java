package com.techelevator.services;

import com.techelevator.model.Restaurant;
import com.techelevator.model.UserRestaurantDto;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> getRestaurants();

    Restaurant getRestaurantById(int restaurantId);

    Restaurant addRestaurant(Restaurant restaurant);

    Restaurant updateRestaurant(Restaurant restaurant);

    List<Restaurant> getRestaurantsByCategory(List<Integer> categoryId, int userId);

    UserRestaurantDto addUserRestaurant(UserRestaurantDto userRestaurantDto);

    List<Restaurant> getUsersLikedRestaurants(int userId);

    List<Restaurant> getUsersRejectedRestaurants(int userId);

    boolean updateUserRestaurant(UserRestaurantDto userRestaurantDto);

    List<Restaurant> getNotLikedOrRejectedRestaurants(int userId);

}
