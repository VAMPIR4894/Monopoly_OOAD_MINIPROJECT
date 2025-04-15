package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public class UserServiceProxy {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CaptchaService captchaService;
    
    /**
     * Register a new user (no captcha required)
     */
    public User registerUser(String username, String password) {
        return userService.registerUser(username, password);
    }
    
    /**
     * Login with captcha verification (proxy pattern)
     */
    public User loginUser(String username, String password, String captchaToken, String captchaInput) {
        // First verify the captcha
        if (!captchaService.validateCaptcha(captchaToken, captchaInput)) {
            throw new IllegalArgumentException("Invalid captcha");
        }
        
        // If captcha is valid, proceed with login
        return userService.loginUser(username, password);
    }
    
    /**
     * Get user by ID (no captcha required)
     */
    public User getUserById(Long userId) {
        return userService.getUserById(userId);
    }
    
    /**
     * Get users in a game (no captcha required)
     */
    public java.util.List<User> getUsersInGame(String gameId) {
        return userService.getUsersInGame(gameId);
    }
} 