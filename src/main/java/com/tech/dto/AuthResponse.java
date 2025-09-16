package com.tech.dto;

public class AuthResponse {

    private String token;

    // A constructor is necessary to easily create the response object with the token
    public AuthResponse(String token) {
        this.token = token;
    }

    // A getter is needed for Spring to serialize the token into the JSON response
    public String getToken() {
        return token;
    }

    // A setter is not strictly necessary for this DTO, but can be added
    public void setToken(String token) {
        this.token = token;
    }
}   