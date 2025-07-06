package com.demo.ipgeolocatorapplication.adapters.out.api;

import com.demo.ipgeolocatorapplication.adapters.out.api.dto.FreeIpApiResponse;
import com.demo.ipgeolocatorapplication.application.model.IpGeoLocatorData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FreeIpApiAdapterLocatorTest {

    @Value("${ipgeo.api.url}")
    private String apiUrl;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FreeIpApiAdapterLocator adapter;

    @Test
    @DisplayName("Should fetch data and map to IpGeoLocatorData")
    void testFetch() {
        FreeIpApiResponse apiResponse = new FreeIpApiResponse(
                "127.0.0.1", "NA", "Country", "Region", "City", 0.0, 0.0);
        when(restTemplate.getForObject(apiUrl + "127.0.0.1", FreeIpApiResponse.class))
                .thenReturn(apiResponse);

        IpGeoLocatorData result = adapter.fetch("127.0.0.1");
        assertNotNull(result);
        assertEquals("127.0.0.1", result.ipAddress());
        assertEquals("NA", result.continent());
        assertEquals("Country", result.country());
        assertEquals("Region", result.region());
        assertEquals("City", result.city());
        assertEquals(0.0, result.latitude());
        assertEquals(0.0, result.longitude());
    }

    @Test
    @DisplayName("Should throw exception when API returns null")
    void testFetchReturnsNull() {
        when(restTemplate.getForObject(apiUrl + "127.0.0.1", FreeIpApiResponse.class))
                .thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> adapter.fetch("127.0.0.1"));
        assertEquals("API returned null", ex.getMessage());
    }
}
