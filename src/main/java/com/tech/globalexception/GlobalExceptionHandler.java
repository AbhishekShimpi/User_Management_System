package com.tech.globalexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handles security-related authentication exceptions
    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(org.springframework.security.authentication.BadCredentialsException ex) {
        return new ResponseEntity<>("Invalid credentials. Please check your email and password.", HttpStatus.UNAUTHORIZED);
    }

    // Handles cases where a user with a given email is not found
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UsernameNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handles JPA/DB constraint violations, such as duplicate email entries
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        // You can check if the cause is a ConstraintViolationException to be more specific
        return new ResponseEntity<>("Data integrity violation: Email is already registered.", HttpStatus.CONFLICT);
    }
    
    // A general handler for all other exceptions as a fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}