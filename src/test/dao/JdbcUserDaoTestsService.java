package com.techelevator.dao;

import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class JdbcUserDaoTestsService extends BaseDaoTests {
    protected static final User USER_1 = new User(1, "user1", "user1","ROLE_USER","Du","Grogu","10029");
    protected static final User USER_2 = new User(2, "user2", "user2", "ROLE_USER","Joe","Do","10025");
    private static final User USER_3 = new User(3, "user3", "user3", "ROLE_USER","Joanne","Do","10023");
    private JdbcUserDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findIdByUsername_given_null_throws_exception() {
        sut.findIdByUsername(null);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void findIdByUsername_given_invalid_username_throws_exception() {
        sut.findIdByUsername("invalid");
    }

    @Test
    public void findIdByUsername_given_valid_user_returns_user_id() {
        int actualUserId = sut.findIdByUsername(USER_1.getUsername());

        Assert.assertEquals(USER_1.getId(), actualUserId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByUsername_given_null_throws_exception() {
        sut.findByUsername(null);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void findByUsername_given_invalid_username_throws_exception() {
        sut.findByUsername("invalid");
    }

    @Test
    public void findByUsername_given_valid_user_returns_user() {
        User actualUser = sut.findByUsername(USER_1.getUsername());

        Assert.assertEquals(USER_1, actualUser);
    }

    public void getUserById_given_invalid_user_id_returns_null() {
        User user = sut.getUserById(-1);

        Assert.assertNull(user);
    }

    @Test
    public void getUserById_given_valid_user_id_returns_user() {
        User actualUser = sut.getUserById(USER_1.getId());

        Assert.assertEquals(USER_1, actualUser);
    }

    @Test
    public void findAll_returns_all_users() {
        List<User> users = sut.findAll();

        Assert.assertNotNull(users);
        Assert.assertEquals(3, users.size());
        Assert.assertEquals(USER_1, users.get(0));
        Assert.assertEquals(USER_2, users.get(1));
        Assert.assertEquals(USER_3, users.get(2));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void create_user_with_null_username() {
        sut.create(null, USER_3.getPassword(), "ROLE_USER", USER_3.getFirstName(),USER_3.getLastName(), USER_3.getZipCode());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void create_user_with_existing_username() {
        sut.create(USER_1.getUsername(), USER_3.getPassword() ,"ROLE_USER", USER_2.getFirstName(),USER_2.getLastName(), USER_2.getZipCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_user_with_null_password() {
        sut.create(USER_3.getUsername(), null, "ROLE_USER", USER_2.getFirstName(),USER_2.getLastName(), USER_2.getZipCode());
    }

    @Test
    public void create_user_creates_a_user() {
        User newUser = new User(-1, "new", "user", "ROLE_USER","Maggie","Luong","10028");

        boolean userWasCreated = sut.create(newUser.getUsername(), newUser.getPassword(), "ROLE_USER", newUser.getFirstName(), newUser.getLastName(), newUser.getZipCode());

        Assert.assertTrue(userWasCreated);

        User actualUser = sut.findByUsername(newUser.getUsername());
        newUser.setId(actualUser.getId());

        actualUser.setPassword(newUser.getPassword()); // reset password back to unhashed password for testing
        Assert.assertEquals(newUser, actualUser);
    }

}
