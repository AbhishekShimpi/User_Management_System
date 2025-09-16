package com.tech;

import com.tech.model.Role;
import com.tech.model.User;
import com.tech.repository.UserRepository;
import com.tech.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Test
    void loadUserByUsername_UserFound_ReturnsUserDetails() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("hashedpassword");
        Role role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(Set.of(role));

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername("test@example.com");

        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals(1, userDetails.getAuthorities().size());
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                userDetailsService.loadUserByUsername("nonexistent@example.com"));
    }
}