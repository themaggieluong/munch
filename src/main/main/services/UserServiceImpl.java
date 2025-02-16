package com.techelevator.services;

import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
    @Override
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
    @Override
    public int findIdByUsername(String username) {
        return userDao.findIdByUsername(username);
    }

    @Override
    public boolean create(String username, String password, String role, String firstName, String lastName, String zipCode) {
        try {
            User user = userDao.findByUsername(username);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Already Exists.");
        } catch (UsernameNotFoundException e) {
            userDao.create(username, password, role, firstName, lastName, zipCode);
            return true;
        }
    }
}
