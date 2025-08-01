package com.javaninja.service;

import com.javaninja.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    @DisplayName("Should return all users")
    void shouldReturnAllUsers() {
        // When
        List<User> users = userService.getAllUsers();

        // Then
        assertNotNull(users);
        assertEquals(3, users.size());
        assertTrue(users.stream().anyMatch(user -> "John Doe".equals(user.getName())));
        assertTrue(users.stream().anyMatch(user -> "Jane Smith".equals(user.getName())));
        assertTrue(users.stream().anyMatch(user -> "Bob Johnson".equals(user.getName())));
    }

    @Test
    @DisplayName("Should return user by valid ID")
    void shouldReturnUserByValidId() {
        // When
        Optional<User> user = userService.getUserById(1L);

        // Then
        assertTrue(user.isPresent());
        assertEquals("John Doe", user.get().getName());
        assertEquals("john.doe@example.com", user.get().getEmail());
    }

    @Test
    @DisplayName("Should return empty optional for invalid ID")
    void shouldReturnEmptyOptionalForInvalidId() {
        // When
        Optional<User> user = userService.getUserById(999L);

        // Then
        assertFalse(user.isPresent());
    }

    @Test
    @DisplayName("Should create new user")
    void shouldCreateNewUser() {
        // Given
        User newUser = new User(null, "Alice Brown", "alice.brown@example.com");

        // When
        User createdUser = userService.createUser(newUser);

        // Then
        assertNotNull(createdUser.getId());
        assertEquals("Alice Brown", createdUser.getName());
        assertEquals("alice.brown@example.com", createdUser.getEmail());
        assertEquals(4, userService.getAllUsers().size());
    }

    @Test
    @DisplayName("Should update existing user")
    void shouldUpdateExistingUser() {
        // Given
        User updatedUser = new User(null, "John Updated", "john.updated@example.com");

        // When
        Optional<User> result = userService.updateUser(1L, updatedUser);

        // Then
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("John Updated", result.get().getName());
        assertEquals("john.updated@example.com", result.get().getEmail());
    }

    @Test
    @DisplayName("Should return empty optional when updating non-existent user")
    void shouldReturnEmptyOptionalWhenUpdatingNonExistentUser() {
        // Given
        User updatedUser = new User(null, "Non Existent", "non.existent@example.com");

        // When
        Optional<User> result = userService.updateUser(999L, updatedUser);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should delete existing user")
    void shouldDeleteExistingUser() {
        // When
        boolean deleted = userService.deleteUser(1L);

        // Then
        assertTrue(deleted);
        assertEquals(2, userService.getAllUsers().size());
        assertFalse(userService.getUserById(1L).isPresent());
    }

    @Test
    @DisplayName("Should return false when deleting non-existent user")
    void shouldReturnFalseWhenDeletingNonExistentUser() {
        // When
        boolean deleted = userService.deleteUser(999L);

        // Then
        assertFalse(deleted);
        assertEquals(3, userService.getAllUsers().size());
    }
}

