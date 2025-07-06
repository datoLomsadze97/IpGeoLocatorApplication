package com.demo.ipgeolocatorapplication.adapters.out.api;

import com.demo.ipgeolocatorapplication.application.model.IpGeoLocatorData;

public interface IpGeoLocatorProvider {
    IpGeoLocatorData fetch(String ip);
}
