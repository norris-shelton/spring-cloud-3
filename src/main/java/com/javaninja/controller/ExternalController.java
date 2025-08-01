package com.javaninja.controller;

import com.javaninja.service.ExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/external")
public class ExternalController {
    
    private final ExternalService externalService;
    
    @Autowired
    public ExternalController(ExternalService externalService) {
        this.externalService = externalService;
    }
    
    @GetMapping("/posts/{id}")
    public ResponseEntity<Map<String, Object>> getPost(@PathVariable Long id) {
        Map<String, Object> post = externalService.getPost(id);
        return ResponseEntity.ok(post);
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> getExternalUser(@PathVariable Long id) {
        Map<String, Object> user = externalService.getExternalUser(id);
        return ResponseEntity.ok(user);
    }
}

