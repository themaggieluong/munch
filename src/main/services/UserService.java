package com.techelevator.services;

import com.techelevator.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User getUserById(int userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password, String role, String firstName, String lastName, String zipCode);

}
