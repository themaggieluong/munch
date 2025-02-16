package com.techelevator.controller;

import com.techelevator.model.User;
import com.techelevator.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/users/", method = RequestMethod.GET)
    public List<User> findAll(){
        return userService.findAll();
    }
    @RequestMapping(path = "/users/id/{userId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }
    @RequestMapping(path = "/users/username/{username}", method = RequestMethod.GET)
    public User findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }
    @RequestMapping(path = "/users/{username}", method = RequestMethod.GET)
    public int findIdByUsername(@PathVariable String username){
        return userService.findIdByUsername(username);
    }


}
