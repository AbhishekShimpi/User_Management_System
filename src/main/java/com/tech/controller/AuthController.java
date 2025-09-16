package com.tech.controller;

import com.tech.model.User;
import com.tech.model.Role;
import com.tech.repository.UserRepository;
import com.tech.repository.RoleRepository;
import com.tech.dto.AuthRequest;
import com.tech.dto.AuthResponse;
import com.tech.dto.RegisterRequest;
import com.tech.jwt.JwtUtil;
import com.tech.service.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository; // Inject RoleRepository
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          CustomUserDetailsService userDetailsService,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            return new ResponseEntity<>("Email already in use", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        // Fetch the default 'ROLE_USER' from the database
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
        
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        // Load UserDetails after successful authentication
        UserDetails userDetails = userDetailsService.loadUserByUsername(req.getEmail());
        
        String token = jwtUtil.generateToken(userDetails);
        
        return ResponseEntity.ok(new AuthResponse(token));
    }
}