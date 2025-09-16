package com.tech;
import com.tech.jwt.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @Test
    void testGenerateTokenAndValidate() {
        // Set the JWT secret using reflection, since it's a private field
        ReflectionTestUtils.setField(jwtUtil, "secretKey", "hK7p9eJ5h2gS4rY0uEw8sX3qB5cK1mD7fA6bN4vR2zT1xL0oI9uP8wXyZ0A1bC2dE3fG4hI5jK6L");
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", 3600000L);

        UserDetails userDetails = new User("testuser@example.com", "password", Collections.emptyList());

        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);

        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals("testuser@example.com", extractedUsername);

        boolean isValid = jwtUtil.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }
}