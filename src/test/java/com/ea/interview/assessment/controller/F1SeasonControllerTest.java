package com.ea.interview.assessment.controller;

import com.ea.interview.assessment.model.DriverStanding;
import com.ea.interview.assessment.model.RaceResults;
import com.ea.interview.assessment.service.F1SeasonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class F1SeasonControllerTest {

    @Mock
    F1SeasonService f1SeasonService;

    F1SeasonController f1SeasonController;

    @Before
    public void setup() {
        f1SeasonController = spy(new F1SeasonController(f1SeasonService));
    }

    @Test
    public void getDriverStandingsTest() {

        List aList = Mockito.mock(List.class);
        when(f1SeasonService.getDriveStandingsByYear(anyString())).thenReturn(aList);
        ResponseEntity<List<DriverStanding>> result = f1SeasonController.getDriverStandings(anyString());
        assertTrue(result.hasBody());
    }


}
