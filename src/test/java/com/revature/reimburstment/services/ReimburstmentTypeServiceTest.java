package com.revature.reimburstment.services;

import com.revature.reimburstment.daos.ReimburstDAO;
import com.revature.reimburstment.daos.ReimburstmentTypeDAO;
import com.revature.reimburstment.models.ReimburstmentType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ReimburstmentTypeServiceTest {

    @Mock
    ReimburstmentTypeDAO reimburstmentTypeDAO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void save_Should_Create_A_Type() throws IOException {

        ReimburstmentTypeService reimburstmentTypeService = new ReimburstmentTypeService(reimburstmentTypeDAO);
        ReimburstmentType rt = new ReimburstmentType();
        when(reimburstmentTypeDAO.save(rt)).thenReturn(new Integer(1));
    }

}