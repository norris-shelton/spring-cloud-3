package com.javaninja.service;

import com.javaninja.model.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    private final List<User> users = new ArrayList<>();
    
    public UserService() {
        // Initialize with some sample data
        users.add(new User(1L, "John Doe", "john.doe@example.com"));
        users.add(new User(2L, "Jane Smith", "jane.smith@example.com"));
        users.add(new User(3L, "Bob Johnson", "bob.johnson@example.com"));
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    public Optional<User> getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
    
    public User createUser(User user) {
        Long newId = users.stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0L) + 1;
        user.setId(newId);
        users.add(user);
        return user;
    }
    
    public Optional<User> updateUser(Long id, User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                updatedUser.setId(id);
                users.set(i, updatedUser);
                return Optional.of(updatedUser);
            }
        }
        return Optional.empty();
    }
    
    public boolean deleteUser(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }
}

