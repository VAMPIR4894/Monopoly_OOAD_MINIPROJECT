package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserServiceProxy;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Allow front-end access
public class AuthRestController {

    @Autowired
    private UserServiceProxy userServiceProxy;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userMap) {
        String username = userMap.get("username");
        String password = userMap.get("password");
        String confirmPassword = userMap.get("confirmPassword");

        if (!password.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        try {
            userServiceProxy.registerUser(username, password);
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String captchaToken = body.get("captchaToken");
        String captchaInput = body.get("captchaInput");

        if (captchaToken == null || captchaInput == null) {
            return ResponseEntity.badRequest().body("Captcha verification required");
        }

        try {
            User user = userServiceProxy.loginUser(username, password, captchaToken, captchaInput);
            return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "userId", user.getId(),
                "username", user.getUsername()
            ));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("captcha")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid captcha");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        }
    }
}
