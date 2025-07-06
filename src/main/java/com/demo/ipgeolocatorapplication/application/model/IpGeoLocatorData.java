package com.demo.ipgeolocatorapplication.application.model;

public record IpGeoLocatorData(String ipAddress,
                               String continent,
                               String country,
                               String region,
                               String city,
                               double latitude,
                               double longitude) {
}
