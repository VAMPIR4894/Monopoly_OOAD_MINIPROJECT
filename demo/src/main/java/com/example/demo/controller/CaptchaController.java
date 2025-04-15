package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CaptchaService;

@RestController
@RequestMapping("/api/captcha")
@CrossOrigin(origins = "*")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;
    
    /**
     * Generate a new captcha and return a token
     */
    @GetMapping("/generate")
    public ResponseEntity<?> generateCaptcha() {
        String token = captchaService.createCaptcha();
        String captchaValue = captchaService.getCaptchaValue(token);
        
        // In a real application, you would generate an image here
        // For simplicity, we'll just return the text value
        return ResponseEntity.ok(Map.of(
            "token", token,
            "captcha", captchaValue
        ));
    }
} 