package com.tech.controller;

import com.tech.model.User;
import com.tech.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
      
    // This endpoint is secured in SecurityConfig with .hasRole("ADMIN")
    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // This endpoint is secured with .hasAnyRole("ADMIN", "USER")
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // This endpoint is secured with .hasRole("ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }     
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully.");
    }
}