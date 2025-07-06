package com.demo.ipgeolocatorapplication.adapters.out.api.dto;

public record FreeIpApiResponse(
        String ipAddress,
        String continent,
        String countryName,
        String regionName,
        String cityName,
        double latitude,
        double longitude
) {
}