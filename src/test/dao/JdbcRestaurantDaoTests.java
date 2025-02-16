package com.techelevator.dao;

import com.techelevator.model.Category;
import com.techelevator.model.Restaurant;
import com.techelevator.model.UserRestaurantDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class JdbcRestaurantDaoTests extends BaseDaoTests {

    protected static final Restaurant RESTAURANT_1 = new Restaurant(1, "Flip Sigi", "131 7th Ave S", "New York", "NY", "10014", "Casual, chef-owned spot for Filipino-style tacos, burritos & sandwiches, plus beer, wine & punch.", 8333547744L, 4.4, "fong-on");
    protected static final Restaurant RESTAURANT_2 = new Restaurant(2, "Wah Fung No.1 Fast Food", "79 Chrystie St", "New York", "NY", "100002", "Roast pork is the specialty at this casual Chinese restaurant for street-style food.", 2129255175L, 4.6, "milu");

    protected static final Category CATEGORY1 = new Category(1, "American");
    protected static final Category CATEGORY2 = new Category(2, "Chinese");
    protected static final Category CATEGORY3 = new Category(2, "Italian");
    private Restaurant testRestaurant;
    private JdbcRestaurantDao sut;
    private UserRestaurantDto testUserRestaurant;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcRestaurantDao(jdbcTemplate);
        testRestaurant = new Restaurant(0, "Rib No. 7", "32 W 33rd St", "New York", "NY", "10001", "Tabletop grills cook up choice cuts at this hip, upscale, Korean-inspired restaurant.", 2123810802L, 4.4, "kikis");
        testUserRestaurant = new UserRestaurantDto();
        testUserRestaurant.setUserId(1);
        testUserRestaurant.setRestaurantId(1);
        testUserRestaurant.setLiked(true);
        testUserRestaurant.setRejected(false);
        testUserRestaurant.setVisitCount(0);
    }

    @Test
    public void getRestaurants_returns_correct_restaurants(){
        List<Restaurant> actual = sut.getRestaurants();
        Assert.assertEquals(RESTAURANT_1, actual.get(0));
        Assert.assertEquals(RESTAURANT_2, actual.get(1));
    }

    @Test
    public void getRestaurantById_returns_correct_restaurant(){
        Restaurant actualRestaurant = sut.getRestaurantById(RESTAURANT_1.getRestaurantId());
        Assert.assertEquals(RESTAURANT_1, actualRestaurant);

    }

    @Test
    public void addRestaurant_returns_restaurant_with_id_and_expected_values(){
        //insert a new restaurant
        Restaurant createdRestaurant = sut.addRestaurant(testRestaurant);

        int newId = createdRestaurant.getRestaurantId();
        Assert.assertTrue(newId > 0);

        Restaurant retrievedRestaurant = sut.getRestaurantById(newId);
        assertRestaurantsMatch(createdRestaurant, retrievedRestaurant);

    }

    @Test
    public void updated_restaurant_has_expected_values_when_retrieved() {
        Restaurant restaurantToUpdate = sut.getRestaurantById(1);

        restaurantToUpdate.setRestaurantName("Updated");
        restaurantToUpdate.setAddress("123 Park Ave");
        restaurantToUpdate.setCity("Wilmington");
        restaurantToUpdate.setState("DE");
        restaurantToUpdate.setZipCode("19810");
        restaurantToUpdate.setDescription("Good food.");
        restaurantToUpdate.setPhoneNumber(1234567891L);
        restaurantToUpdate.setRating(4.7);
        restaurantToUpdate.setImgSrc("fong-on");

        sut.updateRestaurant(restaurantToUpdate);

        Restaurant retrievedRestaurant = sut.getRestaurantById(1);
        assertRestaurantsMatch(restaurantToUpdate, retrievedRestaurant);
    }

    @Test
    public void getRestaurantsByCategory_returns_correct_restaurants(){
        List<Restaurant> actual = sut.getRestaurantsByCategory(CATEGORY2.getCategoryId(), 1);
        Assert.assertEquals(RESTAURANT_2, actual.get(0));
    }

    @Test
    public void addUserRestaurant_returns_correct_userRestaurant(){
        UserRestaurantDto createdUserRestaurant = testUserRestaurant;

        UserRestaurantDto retrievedRestaurant = sut.addUserRestaurant(testUserRestaurant);
        assertUserRestaurantsMatch(createdUserRestaurant, retrievedRestaurant);
    }

    @Test
    public void getUsersLikedRestaurants_returns_correct_restaurants(){
        List<Restaurant> expected = new ArrayList<>();
        Restaurant restaurant = sut.getRestaurantById(1);
        expected.add(restaurant);
        List<Restaurant> retrievedRestaurants = sut.getUsersLikedRestaurants(2);

        assertRestaurantsMatch(expected.get(0), retrievedRestaurants.get(0));
    }

    @Test
    public void getUsersRejectedRestaurants_returns_correct_restaurants(){
        List<Restaurant> expected = new ArrayList<>();
        Restaurant restaurant = sut.getRestaurantById(2);
        expected.add(restaurant);
        List<Restaurant> retrievedRestaurants = sut.getUsersRejectedRestaurants(2);

        assertRestaurantsMatch(expected.get(0), retrievedRestaurants.get(0));
    }

    @Test
    public void getNotLikedOrRejectedRestaurants_returns_correct_restaurants(){
        List<Restaurant> expected = new ArrayList<>();
        Restaurant restaurant = sut.getRestaurantById(3);
        expected.add(restaurant);
        List<Restaurant> retrievedRestaurants = sut.getNotLikedOrRejectedRestaurants(2);

        assertRestaurantsMatch(expected.get(0), retrievedRestaurants.get(0));
    }

    @Test
    public void updateUserRestaurant_returns_correct_response(){
        UserRestaurantDto testDto = new UserRestaurantDto();
        testDto.setUserId(2);
        testDto.setVisitCount(1);
        testDto.setRejected(true);
        testDto.setLiked(false);
        testDto.setRestaurantId(1);
        boolean actual = sut.updateUserRestaurant(testDto);

        Assert.assertTrue(actual);
    }

    private void assertRestaurantsMatch(Restaurant expected, Restaurant actual) {
        Assert.assertEquals(expected.getRestaurantId(), actual.getRestaurantId());
        Assert.assertEquals(expected.getRestaurantName(), actual.getRestaurantName());
        Assert.assertEquals(expected.getAddress(), actual.getAddress());
        Assert.assertEquals(expected.getCity(), actual.getCity());
        Assert.assertEquals(expected.getState(), actual.getState());
        Assert.assertEquals(expected.getZipCode(), actual.getZipCode());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
        Assert.assertEquals(expected.getRating(), actual.getRating(), 0.1);
        Assert.assertEquals(expected.getImgSrc(), actual.getImgSrc());
    }
    private void assertUserRestaurantsMatch(UserRestaurantDto expected, UserRestaurantDto actual){
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getRestaurantId(), actual.getRestaurantId());
        Assert.assertEquals(expected.getVisitCount(), actual.getVisitCount());
        Assert.assertEquals(expected.isLiked(), actual.isLiked());
        Assert.assertEquals(expected.isRejected(), actual.isRejected());
    }
}
