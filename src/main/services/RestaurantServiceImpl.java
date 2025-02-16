package com.techelevator.services;

import com.techelevator.dao.RestaurantDao;
import com.techelevator.model.Restaurant;
import com.techelevator.model.UserRestaurantDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantDao restaurantDao;

    @Autowired
    public RestaurantServiceImpl(RestaurantDao restaurantDao){
        this.restaurantDao = restaurantDao;
    }

    @Override
    public List<Restaurant> getRestaurants(){
        return restaurantDao.getRestaurants();
    }

    @Override
    public Restaurant getRestaurantById(int restaurantId){
        return restaurantDao.getRestaurantById(restaurantId);
    }

    @Override
    public Restaurant addRestaurant(Restaurant restaurant){
        return restaurantDao.addRestaurant(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant){
        return restaurantDao.updateRestaurant(restaurant);
    }


    @Override
    public List<Restaurant> getRestaurantsByCategory(List<Integer> categoryId, int userId){
        List<Restaurant> restaurants = new ArrayList<>();
        for(int id: categoryId){
            restaurants.addAll(restaurantDao.getRestaurantsByCategory(id, userId));
        }
        // Remove duplicate restaurants
        Set<Restaurant> restaurantSet = new HashSet<>();
        restaurantSet.addAll(restaurants);
        restaurants = new ArrayList<>();
        restaurants.addAll(restaurantSet);
        return restaurants;
    }

    @Override
    public UserRestaurantDto addUserRestaurant(UserRestaurantDto userRestaurantDto){
        return restaurantDao.addUserRestaurant(userRestaurantDto);
    }

    @Override
    public List<Restaurant> getUsersLikedRestaurants (int userId){
        return restaurantDao.getUsersLikedRestaurants(userId);
    }

    @Override
    public List<Restaurant> getUsersRejectedRestaurants (int userId){
        return restaurantDao.getUsersRejectedRestaurants(userId);
    }

    @Override
    public boolean updateUserRestaurant (UserRestaurantDto userRestaurantDto){
        return restaurantDao.updateUserRestaurant(userRestaurantDto);
    }

    @Override
    public List<Restaurant> getNotLikedOrRejectedRestaurants(int userId){
        return restaurantDao.getNotLikedOrRejectedRestaurants(userId);
    }
}
