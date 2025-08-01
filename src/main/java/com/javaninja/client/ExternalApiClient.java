package com.javaninja.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "external-api", url = "https://jsonplaceholder.typicode.com")
public interface ExternalApiClient {
    
    @GetMapping("/posts/{id}")
    Map<String, Object> getPost(@PathVariable("id") Long id);
    
    @GetMapping("/users/{id}")
    Map<String, Object> getExternalUser(@PathVariable("id") Long id);
}

