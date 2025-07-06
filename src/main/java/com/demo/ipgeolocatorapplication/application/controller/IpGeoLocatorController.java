package com.demo.ipgeolocatorapplication.application.controller;

import com.demo.ipgeolocatorapplication.application.model.IpGeoLocatorData;
import com.demo.ipgeolocatorapplication.application.service.IpGeoLocatorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/ip-geo-location")
public class IpGeoLocatorController {

    private final IpGeoLocatorService ipGeoLocatorService;

    @GetMapping("/{ip}")
    public IpGeoLocatorData lookup(@PathVariable String ip) {
        return ipGeoLocatorService.lookup(ip);
    }
}
