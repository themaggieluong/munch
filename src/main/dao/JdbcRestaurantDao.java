package com.techelevator.dao;


import com.techelevator.exception.DaoException;
import com.techelevator.model.Restaurant;
import com.techelevator.model.RestaurantCategoryDto;
import com.techelevator.model.UserRestaurantDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcRestaurantDao implements RestaurantDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcRestaurantDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurant;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Restaurant restaurant = mapRowToRestaurant(results);
            restaurants.add(restaurant);
        }

        return restaurants;
    }

    @Override
    public Restaurant getRestaurantById(int restaurantId) {
        String sql = "SELECT * FROM restaurant WHERE restaurant_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, restaurantId);
        if (results.next()) {
            return mapRowToRestaurant(results);
        } else {
            return null;
        }
    }

    @Override
    public Restaurant addRestaurant(Restaurant restaurant) {
        Restaurant newRestaurant = null;
        String sql = "INSERT INTO restaurant (restaurant_name, address, city, state, zip_code, description, phone_number, rating, img_src) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING restaurant_id;";

        try {
            int newRestaurantId = jdbcTemplate.queryForObject(sql, int.class, restaurant.getRestaurantName(), restaurant.getAddress(), restaurant.getCity(), restaurant.getState(), restaurant.getZipCode(), restaurant.getDescription(), restaurant.getPhoneNumber(), restaurant.getRating(), restaurant.getImgSrc());

            newRestaurant = getRestaurantById(newRestaurantId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newRestaurant;
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        Restaurant updatedRestaurant = null;
        String sql = "UPDATE restaurant SET restaurant_name = ?, address = ?, city = ?, state = ?, zip_code = ?, description = ?, phone_number = ?, rating = ?, img_src = ? WHERE restaurant_id = ?;";

        try {
            int numberOfRows = jdbcTemplate.update(sql, restaurant.getRestaurantName(), restaurant.getAddress(), restaurant.getCity(), restaurant.getState(), restaurant.getZipCode(), restaurant.getDescription(), restaurant.getPhoneNumber(), restaurant.getRating(), restaurant.getImgSrc(), restaurant.getRestaurantId());

            if (numberOfRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updatedRestaurant = getRestaurantById(restaurant.getRestaurantId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedRestaurant;
    }


    @Override
    public List<Restaurant> getRestaurantsByCategory(int categoryId, int userId){
        List<Restaurant> restaurantList = new ArrayList<>();
        String sql = "SELECT * FROM restaurant INNER JOIN restaurant_category ON restaurant.restaurant_id = restaurant_category.restaurant_id" +
                " WHERE restaurant.restaurant_id NOT IN (SELECT restaurant_id FROM user_restaurant WHERE user_id = ?) AND restaurant_category.category_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, categoryId);
        while (results.next()){
            Restaurant restaurant = mapRowToRestaurant(results);
            restaurantList.add(restaurant);
        }
        return restaurantList;
    }
    @Override
    public RestaurantCategoryDto addRestaurantByCategory(int restaurantId, int categoryId){
        RestaurantCategoryDto restaurantCategoryDto = new RestaurantCategoryDto();
        String sql = "INSERT INTO restaurant_category(restaurant_id, category_id) VALUES (?,?)";
        jdbcTemplate.queryForObject(sql, int[].class, restaurantId, categoryId);
        restaurantCategoryDto.setRestaurantId(restaurantId);
        restaurantCategoryDto.setCategoryId(categoryId);
        return restaurantCategoryDto;
    }

    @Override
    public UserRestaurantDto addUserRestaurant(UserRestaurantDto userRestaurantDto){
        String sql = "INSERT INTO user_restaurant(user_id, restaurant_id, is_liked, is_rejected, visit_count) VALUES (?, ?, ?, ?, ?);";
         jdbcTemplate.update(sql, userRestaurantDto.getUserId(), userRestaurantDto.getRestaurantId(), userRestaurantDto.isLiked(), userRestaurantDto.isRejected(), userRestaurantDto.getVisitCount());

        return userRestaurantDto;
    }

    @Override
    public List<Restaurant> getUsersLikedRestaurants(int userId){
        List<Restaurant> likedRestaurants = new ArrayList<>();
        String sql = "SELECT * FROM user_restaurant " +
                "INNER JOIN restaurant ON restaurant.restaurant_id = user_restaurant.restaurant_id " +
                "INNER JOIN users ON user_restaurant.user_id = users.user_id " +
                "WHERE user_restaurant.user_id = ? AND user_restaurant.is_liked = true";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()){
            Restaurant restaurant = mapRowToRestaurant(results);
            likedRestaurants.add(restaurant);
        }
        return likedRestaurants;

    }

    @Override
    public List<Restaurant> getNotLikedOrRejectedRestaurants(int userId){
        List<Restaurant> notLikedOrRejected = new ArrayList<>();
        String sql = "SELECT * FROM restaurant " +
                "WHERE restaurant.restaurant_id NOT IN (SELECT restaurant_id FROM user_restaurant WHERE user_id = ?);";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()){
            Restaurant restaurant = mapRowToRestaurant(results);
            notLikedOrRejected.add(restaurant);
        }
        return notLikedOrRejected;
    }
    @Override
    public List<Restaurant> getUsersRejectedRestaurants(int userId){
        List<Restaurant> rejectedRestaurants = new ArrayList<>();
        String sql = "SELECT * FROM user_restaurant " +
                "INNER JOIN restaurant ON restaurant.restaurant_id = user_restaurant.restaurant_id " +
                "INNER JOIN users ON user_restaurant.user_id = users.user_id " +
                "WHERE user_restaurant.user_id = ? AND user_restaurant.is_rejected = true";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()){
            Restaurant restaurant = mapRowToRestaurant(results);
            rejectedRestaurants.add(restaurant);
        }
        return rejectedRestaurants;

    }

    @Override
    public boolean updateUserRestaurant(UserRestaurantDto userRestaurantDto){

        String sql = "UPDATE user_restaurant SET is_liked = ?, is_rejected = ?, visit_count = ? WHERE user_id = ? AND restaurant_id = ?;";
        int numOfRows = jdbcTemplate.update(sql, userRestaurantDto.isLiked(), userRestaurantDto.isRejected(), userRestaurantDto.getVisitCount(), userRestaurantDto.getUserId(), userRestaurantDto.getRestaurantId());
        return numOfRows == 1;
    }




    private Restaurant mapRowToRestaurant(SqlRowSet rs) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(rs.getInt("restaurant_id"));
        restaurant.setRestaurantName(rs.getString("restaurant_name"));
        restaurant.setAddress(rs.getString("address"));
        restaurant.setCity(rs.getString("city"));
        restaurant.setState(rs.getString("state"));
        restaurant.setZipCode(rs.getString("zip_code"));
        restaurant.setDescription(rs.getString("description"));
        restaurant.setPhoneNumber(rs.getLong("phone_number"));
        restaurant.setRating(rs.getDouble("rating"));
        restaurant.setImgSrc(rs.getString("img_src"));
        return restaurant;
    }

}
