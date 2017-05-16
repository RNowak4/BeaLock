package com.wpam.controllers;

import com.wpam.services.BeaconService;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class BeaconControllerTest {
    private MockMvc mockMvc;
    private BeaconController beaconController;
    private BeaconService beaconService;

    @Before
    public void setup() {
        beaconService = Mockito.mock(BeaconService.class);
        beaconController = new BeaconController(beaconService);
        mockMvc = MockMvcBuilders.standaloneSetup(beaconController).build();
    }
}
