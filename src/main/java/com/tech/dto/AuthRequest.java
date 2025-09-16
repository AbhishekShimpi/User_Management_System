package com.tech.dto;

public class AuthRequest {

    private String email;
    private String password;

    // Default constructor is required for deserialization by Spring
    public AuthRequest() {
    }

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
    // Getters and Setters are essential for Spring to bind the JSON
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}   