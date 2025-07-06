package com.demo.ipgeolocatorapplication.application.service;

import com.demo.ipgeolocatorapplication.application.exception.InvalidIpException;
import com.demo.ipgeolocatorapplication.application.exception.IpGeoLookupException;
import com.demo.ipgeolocatorapplication.application.model.IpGeoLocatorData;
import com.demo.ipgeolocatorapplication.adapters.out.api.IpGeoLocatorProvider;
import com.demo.ipgeolocatorapplication.application.rate.RateLimiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

@Service
@RequiredArgsConstructor
public class IpGeoLocatorService {

    private final IpGeoLocatorProvider provider;
    private final RateLimiterService rateLimiter;

    @Cacheable(value = "ipGeoCache", key = "#ip")
    public IpGeoLocatorData lookup(String ip) {
        validateIP(ip);
        rateLimiter.acquire();

        try {
            return provider.fetch(ip);
        } catch (Exception e) {
            throw new IpGeoLookupException(e.getMessage());
        }
    }

    private void validateIP(String ip) {
        try {
            InetAddress.getByName(ip);
        } catch (Exception e) {
            throw new InvalidIpException("Invalid IP address");
        }
    }
}
