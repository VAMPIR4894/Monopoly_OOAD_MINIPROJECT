package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.GameRepository;
import com.example.demo.repository.UserRepository;

@SuppressWarnings("unused")
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GameRepository gameRepository;

    public User registerUser(String username, String password) {
        System.out.println("Registering user: " + username);

        if (userRepository.findByUsername(username) != null) {
            System.out.println("User already exists");
            throw new IllegalArgumentException("User already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User saved = userRepository.save(user);

        System.out.println("User saved: " + saved.getId());
        return saved;
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        throw new IllegalArgumentException("Invalid username or password");
    }
    
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
    
    public List<User> getUsersInGame(String gameId) {
        return userRepository.findUsersByGameId(gameId);
    }
}