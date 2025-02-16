package com.techelevator.dao;

import com.techelevator.model.*;

import java.util.List;

public interface UserDao {
    List<User> findAll();
    User getUserById(int userId);
    User findByUsername(String username);
    int findIdByUsername(String username);

    boolean create(String username, String password, String role, String firstName, String lastName, String zipCode);

}
