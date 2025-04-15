package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class CaptchaService {
    
    // Store captcha tokens and their corresponding values
    private final Map<String, String> captchaStore = new HashMap<>();
    
    // Generate a random captcha string
    public String generateCaptcha() {
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        
        // Generate a 6-character captcha with letters and numbers
        for (int i = 0; i < 6; i++) {
            if (random.nextBoolean()) {
                // Add a random letter
                captcha.append((char) (random.nextInt(26) + 'A'));
            } else {
                // Add a random number
                captcha.append(random.nextInt(10));
            }
        }
        
        return captcha.toString();
    }
    
    // Create a new captcha and return a token
    public String createCaptcha() {
        String captchaValue = generateCaptcha();
        String token = UUID.randomUUID().toString();
        captchaStore.put(token, captchaValue);
        
        // Remove the captcha after 5 minutes (in a real app, you'd use a scheduled task)
        // For simplicity, we'll just store it for now
        
        return token;
    }
    
    // Get the captcha value for a token
    public String getCaptchaValue(String token) {
        return captchaStore.get(token);
    }
    
    // Validate a captcha
    public boolean validateCaptcha(String token, String userInput) {
        String correctValue = captchaStore.get(token);
        if (correctValue != null && correctValue.equalsIgnoreCase(userInput)) {
            // Remove the used captcha
            captchaStore.remove(token);
            return true;
        }
        return false;
    }
} 