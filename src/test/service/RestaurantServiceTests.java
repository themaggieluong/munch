package com.techelevator.service;


import com.techelevator.dao.JdbcRestaurantDao;
import com.techelevator.dao.RestaurantDao;
import com.techelevator.model.Category;
import com.techelevator.model.Restaurant;
import com.techelevator.model.RestaurantCategoryDto;
import com.techelevator.model.UserRestaurantDto;
import com.techelevator.services.RestaurantService;
import com.techelevator.services.RestaurantServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTests {
    RestaurantDao mockRestaurantDao = mock(JdbcRestaurantDao.class);
    RestaurantService sut = new RestaurantServiceImpl(mockRestaurantDao);
    Restaurant restaurant;
    Restaurant getRestaurant1(){
        restaurant = new Restaurant(1, "Da Andrea","35 W 13th St","New York", "NY", "10011", "Fresh-made pastas and home-style meat dishes are the hallmark of this budget Northern Italian spot.", 2123671979L, 4.3, "da-andrea");

        return restaurant;
    }
    Restaurant getRestaurant2(){
        restaurant = new Restaurant(2, "E Noodle Chinatown","26 Jefferson St","New York","NY","10002", "Well known for its friendly staff, excellent service, and outstanding Chinese Cantonese cuisine.",9174092634L,4.5, "e-noodle");
        return restaurant;
    }
    Restaurant getRestaurant3(){
        restaurant = new Restaurant (3, "Kiki's","130 Division St","New York","NY","10002","Straightforward Greek cuisine such as seafood & lamb served in warm digs with exposed-wood beams.",6468827052L,4.1, "kikis");

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

    UserRestaurantDto getUserRestaurantDto2(){
        UserRestaurantDto userRestaurantDto = new UserRestaurantDto();
        userRestaurantDto.setUserId(3);
        userRestaurantDto.setRestaurantId(1);
        userRestaurantDto.setLiked(false);
        userRestaurantDto.setRejected(true);
        userRestaurantDto.setVisitCount(1);

        return userRestaurantDto;
    }

    RestaurantCategoryDto getRestaurantCategory(){
        RestaurantCategoryDto restaurantCategoryDto = new RestaurantCategoryDto();
        restaurantCategoryDto.setCategoryId(getCategory1().getCategoryId());
        restaurantCategoryDto.setRestaurantId(getRestaurant2().getRestaurantId());
        return restaurantCategoryDto;
    }
    @BeforeEach
    void setup(){
        Restaurant restaurant1 = getRestaurant1();
        Restaurant restaurant2 = getRestaurant2();
        sut.addRestaurant(restaurant1);
        sut.addRestaurant(restaurant2);
    }

    @Test
    void getRestaurants_shouldGetRestaurants(){
        List<Restaurant> expected = mockRestaurantDao.getRestaurants();
        when(mockRestaurantDao.getRestaurants()).thenReturn(expected);
        List<Restaurant> actual = sut.getRestaurants();
        Assert.assertEquals(expected, actual);
    }

    @Test
    void getRestaurantById_shouldGetRestaurant(){
        int id = getRestaurant1().getRestaurantId();
        Restaurant expected = mockRestaurantDao.getRestaurantById(id);
        when(mockRestaurantDao.getRestaurantById(id)).thenReturn(expected);
        Restaurant actual = sut.getRestaurantById(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    void getRestaurantsByCategory_shouldGetRestaurants(){
        List<Restaurant> expected = new ArrayList<>();
        expected.add(getRestaurant2());
        int id = getCategory1().getCategoryId();
        when(mockRestaurantDao.getRestaurantsByCategory(id, 1)).thenReturn(expected);
        mockRestaurantDao.addRestaurantByCategory(getRestaurant2().getRestaurantId(), getCategory1().getCategoryId());
        List<Integer> ids = new ArrayList<>();
        ids.add(id);
        List<Restaurant> actual = sut.getRestaurantsByCategory(ids, 1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    void addRestaurant_shouldAddRestaurant(){
        Restaurant expected = getRestaurant3();
        when(mockRestaurantDao.addRestaurant(getRestaurant3())).thenReturn(expected);
        Restaurant actual = sut.addRestaurant(getRestaurant3());
        Assert.assertEquals(expected, actual);
    }

    @Test
    void updateRestaurant_shouldUpdateRestaurant(){
        Restaurant expected = getRestaurant3();
        expected.setRestaurantName("updatedRestaurant");
        when(mockRestaurantDao.updateRestaurant(expected)).thenReturn(expected);
        Restaurant actual = sut.updateRestaurant(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    void addUserRestaurant_shouldAddUserRestaurant(){
        UserRestaurantDto expected = getUserRestaurantDto1();
        when(mockRestaurantDao.addUserRestaurant(expected)).thenReturn(expected);
        UserRestaurantDto actual = sut.addUserRestaurant(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    void getUsersLikedRestaurants_shouldGetLikedRestaurants(){
        List<Restaurant> expected = new ArrayList<>();
        expected.add(getRestaurant2());
        int id = getUserRestaurantDto1().getUserId();
        when(mockRestaurantDao.getUsersLikedRestaurants(id)).thenReturn(expected);
        List<Restaurant> actual = sut.getUsersLikedRestaurants(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    void getUsersRejectedRestaurants_shouldGetRejectedRestaurants(){
        List<Restaurant> expected = new ArrayList<>();
        expected.add(getRestaurant1());
        int id = getUserRestaurantDto1().getUserId();
        when(mockRestaurantDao.getUsersRejectedRestaurants(id)).thenReturn(expected);
        List<Restaurant> actual = sut.getUsersRejectedRestaurants(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    void updateUserRestaurant_shouldUpdateUserRestaurant(){
        UserRestaurantDto userRestaurantDto = getUserRestaurantDto1();
        userRestaurantDto.setVisitCount(3);
        when(mockRestaurantDao.updateUserRestaurant(userRestaurantDto)).thenReturn(true);
        boolean actual = sut.updateUserRestaurant(userRestaurantDto);
        Assert.assertTrue(actual);
    }

    @Test
    void getNotLikedOrRejectedRestaurants_shouldGetRestaurants(){
        List<Restaurant> expected = new ArrayList<>();
        expected.add(getRestaurant1());
        int id = getUserRestaurantDto1().getUserId();
        when(mockRestaurantDao.getNotLikedOrRejectedRestaurants(id)).thenReturn(expected);
        List<Restaurant> actual = sut.getNotLikedOrRejectedRestaurants(id);
        Assert.assertEquals(expected, actual);
    }


}
