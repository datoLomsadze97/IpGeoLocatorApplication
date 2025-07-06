package com.demo.ipgeolocatorapplication.application.config;

import com.demo.ipgeolocatorapplication.application.exception.InvalidIpException;
import com.demo.ipgeolocatorapplication.application.exception.IpGeoLookupException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidIpException.class)
    public ResponseEntity<Map<String, String>> handleInvalidIpException(InvalidIpException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(IpGeoLookupException.class)
    public ResponseEntity<Map<String, String>> handleIpGeoLookupException(IpGeoLookupException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred."));
    }
}
