package com.demo.ipgeolocatorapplication.application.service;

import com.demo.ipgeolocatorapplication.adapters.out.api.IpGeoLocatorProvider;
import com.demo.ipgeolocatorapplication.application.exception.InvalidIpException;
import com.demo.ipgeolocatorapplication.application.exception.IpGeoLookupException;
import com.demo.ipgeolocatorapplication.application.model.IpGeoLocatorData;
import com.demo.ipgeolocatorapplication.application.rate.RateLimiterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IpGeoLocatorServiceTest {

    @Mock
    private IpGeoLocatorProvider provider;

    @Mock
    private RateLimiterService rateLimiter;

    @InjectMocks
    private IpGeoLocatorService service;

    @Test
    @DisplayName("Should return data when IP is valid and provider succeeds")
    void testLookupReturnsData() {
        IpGeoLocatorData expected = new IpGeoLocatorData("127.0.0.1", "NA", "Country", "Region", "City", 0.0, 0.0);
        when(provider.fetch("127.0.0.1")).thenReturn(expected);

        IpGeoLocatorData result = service.lookup("127.0.0.1");
        assertEquals(expected, result);
        verify(provider, times(1)).fetch("127.0.0.1");
        verify(rateLimiter, times(1)).acquire();
    }

    @Test
    @DisplayName("Should throw InvalidIpException for invalid IP")
    void testLookupInvalidIpThrowsException() {
        assertThrows(InvalidIpException.class, () -> service.lookup("invalid_ip"));
        verifyNoInteractions(provider);
        verifyNoInteractions(rateLimiter);
    }

    @Test
    @DisplayName("Should throw IpGeoLookupException when provider fails")
    void testLookupProviderThrowsException() {
        when(provider.fetch("127.0.0.1")).thenThrow(new RuntimeException("Provider error"));
        assertThrows(IpGeoLookupException.class, () -> service.lookup("127.0.0.1"));
        verify(rateLimiter, times(1)).acquire();
    }
}
