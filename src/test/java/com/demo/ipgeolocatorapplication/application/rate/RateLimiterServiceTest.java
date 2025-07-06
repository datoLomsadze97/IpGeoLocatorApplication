package com.demo.ipgeolocatorapplication.application.rate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class RateLimiterServiceTest {

    @Test
    @DisplayName("Should acquire and release permit without exception")
    void testAcquire() {
        RateLimiterService limiter = new RateLimiterService();
        assertDoesNotThrow(() -> {
            limiter.acquire();
            Thread t = new Thread(limiter::acquire);
            t.start();
            t.join(200);
        });
    }
}
