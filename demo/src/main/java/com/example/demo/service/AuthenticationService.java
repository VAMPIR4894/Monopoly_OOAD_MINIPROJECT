package com.example.demo.service;

import com.example.demo.model.User;

public interface AuthenticationService {
    /**
     * Authenticate a user with username and password
     * @param username The username to authenticate
     * @param password The password to verify
     * @return The authenticated user if successful
     * @throws IllegalArgumentException if authentication fails
     */
    User authenticate(String username, String password);
    
    /**
     * Register a new user
     * @param username The username for the new user
     * @param password The password for the new user
     * @return The newly created user
     * @throws IllegalArgumentException if registration fails
     */
    User register(String username, String password);
} 