package com.techelevator.controller;

import com.techelevator.model.Restaurant;
import com.techelevator.model.UserRestaurantDto;
import com.techelevator.services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class RestaurantController {

    private final RestaurantService restaurantHandler;

    public RestaurantController(RestaurantService restaurantHandler) {
        this.restaurantHandler = restaurantHandler;
    }

    @GetMapping(value = "/",
            produces = "application/json")
    public List<Restaurant> getRestaurants() {
        return this.restaurantHandler.getRestaurants();
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getRestaurantById(@PathVariable int id) {
        return this.restaurantHandler.getRestaurantById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value="/", consumes = "application/json", produces = "application/json")
    public Restaurant addRestaurant(@Valid @RequestBody final Restaurant restaurant){
        return restaurantHandler.addRestaurant(restaurant);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(value="/{id}", consumes = "application/json", produces = "application/json")
    public Restaurant updateRestaurant(@Valid @RequestBody final Restaurant restaurant){
        return restaurantHandler.updateRestaurant(restaurant);
    }

    @PostMapping (value = "/restaurant/categories/{id}", consumes="application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getRestaurantsByCategoryIds(@RequestBody List<Integer> categoryId, @PathVariable int id) {
        return this.restaurantHandler.getRestaurantsByCategory(categoryId, id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value="/restaurant/users", consumes = "application/json", produces = "application/json")
    public UserRestaurantDto addUserRestaurantDto(@RequestBody UserRestaurantDto userRestaurantDto){
        return this.restaurantHandler.addUserRestaurant(userRestaurantDto);
    }

    @GetMapping(value = "/restaurant/users/liked/{userId}",
            produces = "application/json")
    public List<Restaurant> getUsersLikedRestaurants(@PathVariable int userId){
        return this.restaurantHandler.getUsersLikedRestaurants(userId);
    }

    @GetMapping(value = "/restaurant/users/rejected/{userId}",
            produces = "application/json")
    public List<Restaurant> getUsersRejectedRestaurants(@PathVariable int userId){
        return this.restaurantHandler.getUsersRejectedRestaurants(userId);
    }

    @GetMapping(value = "/restaurant/updated/{userId}",
            produces = "application/json")
    public List<Restaurant> getNotLikedOrRejectedRestaurants(@PathVariable int userId){
        return this.restaurantHandler.getNotLikedOrRejectedRestaurants(userId);
    }


    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(value="/restaurant/users", consumes = "application/json")
    public boolean updateUserRestaurant(@RequestBody UserRestaurantDto userRestaurantDto){
        return this.restaurantHandler.updateUserRestaurant(userRestaurantDto);
    }


}
