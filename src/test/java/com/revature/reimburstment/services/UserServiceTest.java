package com.revature.reimburstment.services;

import com.revature.reimburstment.daos.UserDAO;
import com.revature.reimburstment.models.User;
import org.junit.Test;

import static org.junit.Assert.*;


public class UserServiceTest {
    @Test
    public void setup() {

    }

    @Test
    public void isValidUsernameTestEqual() {
        UserService userService = new UserService(new UserDAO());
        assertEquals(true, userService.isValidUsername("jnguyen85"));    }

    @Test
    public void isValidUsernameTestFalse() {
        UserService userService = new UserService(new UserDAO());
        assertTrue(userService.isValidUsername("jnguyen85"));}


}