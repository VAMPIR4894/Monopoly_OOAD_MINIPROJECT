package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public class AuthenticationProxy implements AuthenticationService {
    
    @Autowired
    private RealAuthenticationService realAuthenticationService;
    
    @Autowired
    private CaptchaService captchaService;
    
    @Override
    public User authenticate(String username, String password) {
        // This method should not be called directly - use authenticateWithCaptcha instead
        throw new UnsupportedOperationException("Please use authenticateWithCaptcha method");
    }
    
    /**
     * Authenticate a user with username, password and captcha verification
     * @param username The username to authenticate
     * @param password The password to verify
     * @param captchaToken The captcha token
     * @param captchaInput The user's captcha input
     * @return The authenticated user if successful
     * @throws IllegalArgumentException if authentication or captcha verification fails
     */
    public User authenticateWithCaptcha(String username, String password, String captchaToken, String captchaInput) {
        // First verify the captcha
        if (!captchaService.validateCaptcha(captchaToken, captchaInput)) {
            throw new IllegalArgumentException("Invalid captcha");
        }
        
        // If captcha is valid, proceed with real authentication
        return realAuthenticationService.authenticate(username, password);
    }
    
    @Override
    public User register(String username, String password) {
        // Registration doesn't require captcha verification
        return realAuthenticationService.register(username, password);
    }
} 