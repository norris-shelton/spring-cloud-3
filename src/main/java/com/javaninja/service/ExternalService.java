package com.javaninja.service;

import com.javaninja.client.ExternalApiClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExternalService {
    
    private final ExternalApiClient externalApiClient;
    
    @Autowired
    public ExternalService(ExternalApiClient externalApiClient) {
        this.externalApiClient = externalApiClient;
    }
    
    @CircuitBreaker(name = "external-api", fallbackMethod = "fallbackGetPost")
    public Map<String, Object> getPost(Long id) {
        return externalApiClient.getPost(id);
    }
    
    @CircuitBreaker(name = "external-api", fallbackMethod = "fallbackGetExternalUser")
    public Map<String, Object> getExternalUser(Long id) {
        return externalApiClient.getExternalUser(id);
    }
    
    public Map<String, Object> fallbackGetPost(Long id, Exception ex) {
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("id", id);
        fallback.put("title", "Fallback Post");
        fallback.put("body", "This is a fallback response due to service unavailability");
        fallback.put("userId", 1);
        fallback.put("error", "External service is currently unavailable");
        return fallback;
    }
    
    public Map<String, Object> fallbackGetExternalUser(Long id, Exception ex) {
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("id", id);
        fallback.put("name", "Fallback User");
        fallback.put("email", "fallback@example.com");
        fallback.put("error", "External service is currently unavailable");
        return fallback;
    }
}

