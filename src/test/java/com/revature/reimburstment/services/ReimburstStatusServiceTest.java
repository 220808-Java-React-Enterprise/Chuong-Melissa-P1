package com.revature.reimburstment.services;

import com.revature.reimburstment.daos.ReimburstStatusDAO;
import com.revature.reimburstment.dtos.requests.ReimburstmentStatusRequest;
import com.revature.reimburstment.models.ReimburstmentStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReimburstStatusServiceTest {

    @Mock
    ReimburstStatusDAO reimburstStatusDAO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void save_Should_Create_A_Status() throws IOException {

        ReimburstStatusService reimburstStatusService = new ReimburstStatusService(reimburstStatusDAO);

        ReimburstmentStatus rs = new ReimburstmentStatus();
        rs.setStastus_id("0b36d9b9-bfd4-4a65-ab80-9d341a05e217");
        rs.setStatus("PENDING");

        when(reimburstStatusDAO.save(rs)).thenReturn(new Integer(1));

        ReimburstmentStatusRequest reimburstmentStatusRequest = new ReimburstmentStatusRequest();
        reimburstmentStatusRequest.setStatus_id("0b36d9b9-bfd4-4a65-ab80-9d341a05e217");
        reimburstmentStatusRequest.setStatus("PENDING");
        reimburstStatusService.saveReimburstmentStatus(reimburstmentStatusRequest);
        //verify(reimburstStatusDAO).save(rs);

    }

    @Test
    public void update_Should_Update_A_Status() throws IOException {

        ReimburstStatusService reimburstStatusService = new ReimburstStatusService(reimburstStatusDAO);

        ReimburstmentStatusRequest reimburstmentStatusRequest = new ReimburstmentStatusRequest();
        ReimburstmentStatus rs = new ReimburstmentStatus();

        when(reimburstStatusDAO.update(rs)).thenReturn(new Integer(1));

        boolean result = reimburstStatusService.update(reimburstmentStatusRequest);



        //assertTrue(result);


    }

}