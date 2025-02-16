package com.techelevator.service;

import com.techelevator.dao.JdbcUserDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import com.techelevator.services.UserService;
import com.techelevator.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    UserDao mockUserDao = mock(JdbcUserDao.class);
    UserService sut = new UserServiceImpl(mockUserDao);
    User user;

    User getUser1(){
        user = new User(1, "user1", "user1","ROLE_USER","John","Doe","10026");
        return user;
    }
    User getUser2(){
        user = new User(2, "user2", "user2","ROLE_USER","Doe","John","10026");
        return user;
    }

    @BeforeEach
    void setup(){
        User user1 = getUser1();
        User user2 = getUser2();
        sut.create(user1.getUsername(), user1.getPassword(), user1.getAuthorities().toString(), user1.getFirstName(), user1.getLastName(), user1.getZipCode());
        sut.create(user2.getUsername(), user2.getPassword(), user2.getAuthorities().toString(), user2.getFirstName(), user2.getLastName(), user2.getZipCode());
    }

    @Test
    public void create_shouldReturnTrue(){
        String username = getUser1().getUsername();
        String password = getUser1().getPassword();
        String authorities = getUser1().getAuthorities().toString();
        String firstName = getUser1().getFirstName();
        String lastName = getUser1().getLastName();
        String zipCode = getUser1().getZipCode();

        boolean expected = true;
        when(mockUserDao.create(username,password,authorities,firstName,lastName,zipCode)).thenReturn(expected);
        boolean actual = true;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void create_shouldReturnFalse(){
        String username = getUser1().getUsername();
        String password = getUser1().getPassword();
        String authorities = getUser1().getAuthorities().toString();
        String firstName = getUser1().getFirstName();
        String lastName = getUser1().getLastName();
        String zipCode = getUser1().getZipCode();

        boolean expected = false;
        when(mockUserDao.create(username,password,authorities,firstName,lastName,zipCode)).thenReturn(expected);
        boolean actual = false;
        Assert.assertEquals(expected,actual);
    }


    @Test
    public void findAll_shouldFindAllUsers(){
        List<User> expected = mockUserDao.findAll();
        when(mockUserDao.findAll()).thenReturn(expected);

        List<User> actual = sut.findAll();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getUserById_shouldGetUserById(){
        int userId = getUser1().getId();

        User expected = mockUserDao.getUserById(userId);
        when(mockUserDao.getUserById(userId)).thenReturn(expected);

        User actual = sut.getUserById(userId);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByUsername_shouldFindByUsername(){
        String username = getUser1().getUsername();

        User expected = mockUserDao.findByUsername(username);
        when(mockUserDao.findByUsername(username)).thenReturn(expected);

        User actual = sut.findByUsername(username);

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void findIdByUsername_shouldFindIdByUsername(){
        String username = getUser1().getUsername();

        int expected = mockUserDao.findIdByUsername(username);
        when(mockUserDao.findIdByUsername(username)).thenReturn(expected);

        int actual = sut.findIdByUsername(username);

        Assert.assertEquals(expected,actual);
    }

}
