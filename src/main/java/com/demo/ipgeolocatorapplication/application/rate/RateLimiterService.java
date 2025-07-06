package com.demo.ipgeolocatorapplication.application.rate;

import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimiterService {

    private final Semaphore semaphore = new Semaphore(1);
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void acquire() {
        try {
            semaphore.acquire();
            scheduler.schedule(() -> semaphore.release(), 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
