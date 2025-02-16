package com.techelevator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Category;
import com.techelevator.model.UserRestaurantDto;
import com.techelevator.security.JwtAccessDeniedHandler;
import com.techelevator.security.JwtAuthenticationEntryPoint;
import com.techelevator.security.UserModelDetailsService;
import com.techelevator.security.jwt.TokenProvider;
import com.techelevator.services.RestaurantService;
import com.techelevator.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    RestaurantService restaurantService;

    @MockBean
    TokenProvider mockTokenProvider;
    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @MockBean
    UserModelDetailsService userModelDetailsService;

    ObjectMapper objectMapper = new ObjectMapper();

    Restaurant getRestaurant(){
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1);
        restaurant.setRestaurantName("Joe's Steam Rice Roll");
        restaurant.setAddress("136-21 Roosevelt Ave");
        restaurant.setCity("Queens");
        restaurant.setState("NY");
        restaurant.setZipCode("11354");
        restaurant.setDescription("Fast-food restaurant specializing in Cantonese rice noodle dish inside mini-mall space.");
        restaurant.setPhoneNumber(6462037380L);
        restaurant.setRating(4.4);
        restaurant.setImgSrc("fong-on");

        return restaurant;
    }

    Category getCategory1(){
        Category category = new Category();
        category.setCategoryId(2);
        category.setCategoryName("Chinese");
        return category;
    }

    UserRestaurantDto getUserRestaurantDto1(){
        UserRestaurantDto userRestaurantDto = new UserRestaurantDto();
        userRestaurantDto.setUserId(3);
        userRestaurantDto.setRestaurantId(2);
        userRestaurantDto.setLiked(true);
        userRestaurantDto.setRejected(false);
        userRestaurantDto.setVisitCount(1);

        return userRestaurantDto;
    }

    @Test
    public void getRestaurants_gives_correct_response_and_mapping() throws Exception{
        List<Restaurant> restaurants = Arrays.asList(getRestaurant());

        when (restaurantService.getRestaurants()).thenReturn(restaurants);
        ResultActions result = mvc.perform(get("/").accept(MediaType.APPLICATION_JSON));

        verify(restaurantService, times(1)).getRestaurants();
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(content().json(objectMapper.writeValueAsString(restaurants)));

    }

    @Test
    public void getRestaurantById_gives_correct_response_and_mapping() throws Exception{
        Restaurant restaurant = getRestaurant();
        int id = restaurant.getRestaurantId();


        when (restaurantService.getRestaurantById(id)).thenReturn(restaurant);
        ResultActions result = mvc.perform(get("/"+id).accept(MediaType.APPLICATION_JSON));

        verify(restaurantService, times(1)).getRestaurantById(id);
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(content().json(objectMapper.writeValueAsString(restaurant)));

    }

    @Test
    public void addRestaurant_gives_correct_response_and_mapping() throws Exception{
        Restaurant restaurant = getRestaurant();

        when (restaurantService.addRestaurant(restaurant)).thenReturn(restaurant);
        ResultActions result = mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurant)));

        verify(restaurantService, times(1)).addRestaurant(any(Restaurant.class));
        result.andExpect(status().isCreated());

    }

    @Test
    public void updateRestaurant_gives_correct_response_and_mapping() throws Exception{
        Restaurant restaurant = getRestaurant();
        restaurant.setRestaurantName("Updated Name");

        when (restaurantService.updateRestaurant(restaurant)).thenReturn(restaurant);
        ResultActions result = mvc.perform(put("/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurant)));

        verify(restaurantService, times(1)).updateRestaurant(any(Restaurant.class));
        result.andExpect(status().isAccepted());

    }

    @Test
    public void getRestaurantsByCategory_gives_correct_response_and_mapping() throws Exception{
        List<Restaurant> restaurants = Arrays.asList(getRestaurant());

        List<Integer> id = new ArrayList<>();
        id.add(getCategory1().getCategoryId());
        when (restaurantService.getRestaurantsByCategory(id, 1)).thenReturn(restaurants);
        ResultActions result = mvc.perform(post("/restaurant/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(id)));

        verify(restaurantService, times(1)).getRestaurantsByCategory(id, 1);
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(content().json(objectMapper.writeValueAsString(restaurants)));

    }

    @Test
    public void addUserRestaurantDto_gives_correct_response_and_mapping() throws Exception{
        UserRestaurantDto userRestaurantDto = getUserRestaurantDto1();

        when (restaurantService.addUserRestaurant(userRestaurantDto)).thenReturn(userRestaurantDto);
        ResultActions result = mvc.perform(post("/restaurant/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRestaurantDto)));

        verify(restaurantService, times(1)).addUserRestaurant(any(UserRestaurantDto.class));
        result.andExpect(status().isCreated());

    }

    @Test
    public void getUsersLikedRestaurants_gives_correct_response_and_mapping() throws Exception{
        UserRestaurantDto userRestaurantDto = getUserRestaurantDto1();
        List<Restaurant> restaurantList = Arrays.asList(getRestaurant());

        when(restaurantService.getUsersLikedRestaurants(userRestaurantDto.getUserId())).thenReturn(restaurantList);
        ResultActions result = mvc.perform(get("/restaurant/users/liked/3").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRestaurantDto)));

        verify(restaurantService, times(1)).getUsersLikedRestaurants(3);
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(content().json(objectMapper.writeValueAsString(restaurantList)));

    }

    @Test
    public void getUsersRejectedRestaurants_gives_correct_response_and_mapping() throws Exception{
        UserRestaurantDto userRestaurantDto = getUserRestaurantDto1();
        List<Restaurant> restaurantList = Arrays.asList(getRestaurant());

        when(restaurantService.getUsersRejectedRestaurants(userRestaurantDto.getUserId())).thenReturn(restaurantList);
        ResultActions result = mvc.perform(get("/restaurant/users/rejected/3").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRestaurantDto)));

        verify(restaurantService, times(1)).getUsersRejectedRestaurants(3);
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(content().json(objectMapper.writeValueAsString(restaurantList)));


    }


    @Test
    public void getNotLikedOrRejectedRestaurants_gives_correct_response_and_mapping() throws Exception{
        UserRestaurantDto userRestaurantDto = getUserRestaurantDto1();
        List<Restaurant> restaurantList = Arrays.asList(getRestaurant());


        when(restaurantService.getNotLikedOrRejectedRestaurants(userRestaurantDto.getUserId())).thenReturn(restaurantList);
        ResultActions result = mvc.perform(get("/restaurant/updated/3").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRestaurantDto)));

        verify(restaurantService, times(1)).getNotLikedOrRejectedRestaurants(3);
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(content().json(objectMapper.writeValueAsString(restaurantList)));


    }

    @Test
    public void updateUserRestaurant_gives_correct_response_and_mapping() throws Exception{
        UserRestaurantDto userRestaurantDto = getUserRestaurantDto1();
        userRestaurantDto.setVisitCount(3);

        when (restaurantService.updateUserRestaurant(userRestaurantDto)).thenReturn(true);
        ResultActions result = mvc.perform(put("/restaurant/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRestaurantDto)));


        verify(restaurantService, times(1)).updateUserRestaurant(any(UserRestaurantDto.class));
        result.andExpect(status().isAccepted());

    }


}

