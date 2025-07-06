# Ip Geo Locator

## Overview
Application provides a REST API for IP geolocation lookup. It integrates external IP geolocation providers, caches results using Redis, and enforces API rate limiting on cache misses.

## Architecture
- Hexagonal (Ports & Adapters) architecture.
- Domain logic is decoupled from external systems using ports and adapters.
- REST controllers and provider clients are adapters.
- Service layer orchestrates calls between domain, cache, and rate limiter.
- Redis is used as the caching mechanism.
- Rate limiting is handled via an in-memory limiter (can be extended for distributed limits).

## Features
- REST API endpoint for IP geolocation (with versioning)
- External provider integration (e.g., FreeIPAPI, ip-api.com)
- Redis caching (30-day TTL)
- Rate limiter using `Semaphore` and `ScheduledExecutorService` (1 request/sec on cache miss)
- Global exception handling

## Prerequisites
- Java 21
- Maven
- Docker (for Redis + app via Docker Compose)

## Build & Run
### Build the app
```bash
./mvnw clean package
```

### Run with Docker Compose
```bash
docker-compose up --build
```

### Docker Compose services
- `ip-geo-locator-app`: The Spring Boot application (port 8080)
- `ip-geo-locator-redis`: Redis server (port 6379)

## API Usage
- `GET /api/v1/ip-geo-location/{ip}` — Lookup geolocation data for the provided IP address.

## Notes
- Rate limiting applies only to cache misses.
- Update `RateLimiterService` or switch to a distributed limiter if needed.
