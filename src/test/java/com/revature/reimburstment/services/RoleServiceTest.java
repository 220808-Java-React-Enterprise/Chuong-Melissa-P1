package com.revature.reimburstment.services;

import com.revature.reimburstment.daos.RoleDAO;
import com.revature.reimburstment.models.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RoleServiceTest {

    @Mock
    RoleDAO roleDAO;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void save_Should_Create_A_Role() throws IOException {
        RoleService roleService = new RoleService(roleDAO);

        UserRole userRole = new UserRole();
        when(roleDAO.save(userRole)).thenReturn(new Integer(1));
    }
}