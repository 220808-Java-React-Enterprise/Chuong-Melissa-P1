package com.revature.reimburstment.services;

import com.revature.reimburstment.daos.UserDAO;
import com.revature.reimburstment.models.User;
import com.revature.reimburstment.utils.custom_exceptions.InvalidRequestException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import javax.xml.bind.DatatypeConverter;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


public class UserServiceTest {

    @Mock
    UserDAO userDAO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void register_should_create_A_User() {
        UserService userService = new UserService(userDAO);
        User user = new User();
        when(userDAO.save(user)).thenReturn(new Integer(1));
    }
    @Test
    public void test_validateUsername_givenCorrectUsername() {
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String username = "nguyen85";
        UserService.validateUsername(username);
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