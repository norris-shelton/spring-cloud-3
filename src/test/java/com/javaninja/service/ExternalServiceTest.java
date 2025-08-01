package com.javaninja.service;

import com.javaninja.client.ExternalApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExternalServiceTest {

    @Mock
    private ExternalApiClient externalApiClient;

    private ExternalService externalService;

    @BeforeEach
    void setUp() {
        externalService = new ExternalService(externalApiClient);
    }

    @Test
    @DisplayName("Should return post from external API")
    void shouldReturnPostFromExternalApi() {
        // Given
        Long postId = 1L;
        Map<String, Object> expectedPost = new HashMap<>();
        expectedPost.put("id", postId);
        expectedPost.put("title", "Test Post");
        expectedPost.put("body", "Test Body");
        expectedPost.put("userId", 1);

        when(externalApiClient.getPost(postId)).thenReturn(expectedPost);

        // When
        Map<String, Object> result = externalService.getPost(postId);

        // Then
        assertNotNull(result);
        assertEquals(postId, result.get("id"));
        assertEquals("Test Post", result.get("title"));
        assertEquals("Test Body", result.get("body"));
        verify(externalApiClient, times(1)).getPost(postId);
    }

    @Test
    @DisplayName("Should return external user from API")
    void shouldReturnExternalUserFromApi() {
        // Given
        Long userId = 1L;
        Map<String, Object> expectedUser = new HashMap<>();
        expectedUser.put("id", userId);
        expectedUser.put("name", "External User");
        expectedUser.put("email", "external.user@example.com");

        when(externalApiClient.getExternalUser(userId)).thenReturn(expectedUser);

        // When
        Map<String, Object> result = externalService.getExternalUser(userId);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.get("id"));
        assertEquals("External User", result.get("name"));
        assertEquals("external.user@example.com", result.get("email"));
        verify(externalApiClient, times(1)).getExternalUser(userId);
    }

    @Test
    @DisplayName("Should return fallback response for post")
    void shouldReturnFallbackResponseForPost() {
        // Given
        Long postId = 1L;
        RuntimeException exception = new RuntimeException("Service unavailable");

        // When
        Map<String, Object> result = externalService.fallbackGetPost(postId, exception);

        // Then
        assertNotNull(result);
        assertEquals(postId, result.get("id"));
        assertEquals("Fallback Post", result.get("title"));
        assertEquals("This is a fallback response due to service unavailability", result.get("body"));
        assertEquals(1, result.get("userId"));
        assertEquals("External service is currently unavailable", result.get("error"));
    }

    @Test
    @DisplayName("Should return fallback response for external user")
    void shouldReturnFallbackResponseForExternalUser() {
        // Given
        Long userId = 1L;
        RuntimeException exception = new RuntimeException("Service unavailable");

        // When
        Map<String, Object> result = externalService.fallbackGetExternalUser(userId, exception);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.get("id"));
        assertEquals("Fallback User", result.get("name"));
        assertEquals("fallback@example.com", result.get("email"));
        assertEquals("External service is currently unavailable", result.get("error"));
    }
}

