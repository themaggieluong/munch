package com.techelevator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import com.techelevator.security.JwtAccessDeniedHandler;
import com.techelevator.security.JwtAuthenticationEntryPoint;
import com.techelevator.security.UserModelDetailsService;
import com.techelevator.security.jwt.TokenProvider;
import com.techelevator.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTests {
    @Autowired
    MockMvc mvc;
    @MockBean
    UserService userService;
    @MockBean
    TokenProvider mockTokenProvider;
    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @MockBean
    UserModelDetailsService userModelDetailsService;

    ObjectMapper objectMapper = new ObjectMapper();

    User getUser1(){
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("user");
        user.setAuthorities("ROLE_USER");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setZipCode("10029");
        return user;
    }

    User getUser2(){
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("user");
        user.setAuthorities("ROLE_USER");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setZipCode("10029");
        return user;
    }

    @Test
    public void findAll_gives_correct_response_and_mapping() throws Exception{
        List<User> usersList = Arrays.asList(getUser1(),getUser2());

        when(userService.findAll()).thenReturn(usersList);
        ResultActions result = mvc.perform(get("/users/"));

        verify(userService, times(1)).findAll();
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(content().json(objectMapper.writeValueAsString(usersList)));
    }
    @Test
    public void getUserById_gives_correct_response_and_mapping() throws Exception{
        User user = getUser1();
        int userId = user.getId();

        when(userService.getUserById(userId)).thenReturn(user);
        ResultActions result = mvc.perform(get("/users/id/"+userId));

        verify(userService, times(1)).getUserById(userId);
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    public void findByUsername_gives_correct_response_and_mapping() throws Exception{
        User user = getUser1();
        String username = getUser1().getUsername();

        when(userService.findByUsername(username)).thenReturn(user);
        ResultActions result = mvc.perform(get("/users/username/"+username));

        verify(userService, times(1)).findByUsername(username);
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    public void findIdByUsername_gives_correct_response_and_mapping() throws Exception{
        int userId = 0;
        User user = getUser1();
        String username = getUser1().getUsername();

        when(userService.findIdByUsername(username)).thenReturn(userId);
        ResultActions result = mvc.perform(get("/users/"+username));

        verify(userService, times(1)).findIdByUsername(username);
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
