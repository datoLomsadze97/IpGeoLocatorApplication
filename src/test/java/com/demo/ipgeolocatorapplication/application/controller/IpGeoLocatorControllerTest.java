package com.demo.ipgeolocatorapplication.application.controller;

import com.demo.ipgeolocatorapplication.application.model.IpGeoLocatorData;
import com.demo.ipgeolocatorapplication.application.service.IpGeoLocatorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class IpGeoLocatorControllerTest {

    @Mock
    private IpGeoLocatorService ipGeoLocatorService;

    @InjectMocks
    private IpGeoLocatorController controller;

    @Test
    @DisplayName("Should return data for valid IP")
    void testLookup() throws Exception {
        IpGeoLocatorData data = new IpGeoLocatorData("127.0.0.1", "NA", "Country", "Region", "City", 0.0, 0.0);
        when(ipGeoLocatorService.lookup("127.0.0.1")).thenReturn(data);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/api/v1/ip-geo-location/127.0.0.1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(ipGeoLocatorService, times(1)).lookup("127.0.0.1");
    }
}
