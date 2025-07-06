package com.demo.ipgeolocatorapplication.adapters.out.api;

import com.demo.ipgeolocatorapplication.adapters.out.api.dto.FreeIpApiResponse;
import com.demo.ipgeolocatorapplication.application.model.IpGeoLocatorData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class FreeIpApiAdapterLocator implements IpGeoLocatorProvider {

    private final RestTemplate restTemplate;

    @Value("${ipgeo.api.url}")
    private String apiUrl;

    public IpGeoLocatorData fetch(String ip) {
        FreeIpApiResponse response = restTemplate.getForObject(apiUrl + ip, FreeIpApiResponse.class);
        if (response == null) throw new RuntimeException("API returned null");
        return new IpGeoLocatorData(
                response.ipAddress(),
                response.continent(),
                response.countryName(),
                response.regionName(),
                response.cityName(),
                response.latitude(),
                response.longitude()
        );
    }
}
