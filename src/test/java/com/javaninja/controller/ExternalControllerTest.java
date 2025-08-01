package com.javaninja.controller;

import com.javaninja.service.ExternalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExternalController.class)
@ActiveProfiles("test")
class ExternalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExternalService externalService;

    @Test
    @DisplayName("Should return external post")
    void shouldReturnExternalPost() throws Exception {
        // Given
        Long postId = 1L;
        Map<String, Object> post = new HashMap<>();
        post.put("id", postId);
        post.put("title", "Test Post");
        post.put("body", "Test Body");
        post.put("userId", 1);

        when(externalService.getPost(postId)).thenReturn(post);

        // When & Then
        mockMvc.perform(get("/api/external/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Post"))
                .andExpect(jsonPath("$.body").value("Test Body"))
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    @DisplayName("Should return external user")
    void shouldReturnExternalUser() throws Exception {
        // Given
        Long userId = 1L;
        Map<String, Object> user = new HashMap<>();
        user.put("id", userId);
        user.put("name", "External User");
        user.put("email", "external.user@example.com");

        when(externalService.getExternalUser(userId)).thenReturn(user);

        // When & Then
        mockMvc.perform(get("/api/external/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("External User"))
                .andExpect(jsonPath("$.email").value("external.user@example.com"));
    }

    @Test
    @DisplayName("Should return fallback response when external service fails")
    void shouldReturnFallbackResponseWhenExternalServiceFails() throws Exception {
        // Given
        Long postId = 1L;
        Map<String, Object> fallbackPost = new HashMap<>();
        fallbackPost.put("id", postId);
        fallbackPost.put("title", "Fallback Post");
        fallbackPost.put("body", "This is a fallback response due to service unavailability");
        fallbackPost.put("userId", 1);
        fallbackPost.put("error", "External service is currently unavailable");

        when(externalService.getPost(postId)).thenReturn(fallbackPost);

        // When & Then
        mockMvc.perform(get("/api/external/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Fallback Post"))
                .andExpect(jsonPath("$.error").value("External service is currently unavailable"));
    }
}

