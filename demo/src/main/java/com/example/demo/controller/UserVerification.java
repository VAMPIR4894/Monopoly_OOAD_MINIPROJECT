package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Game;
import com.example.demo.model.User;
import com.example.demo.service.GameService;
import com.example.demo.service.UserServiceProxy;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class UserVerification {

    @Autowired
    private UserServiceProxy userServiceProxy;
    
    @Autowired
    private GameService gameService;
    
    @PostMapping("/create")
    public ResponseEntity<?> createGame(@RequestBody Map<String, String> gameData) {
        String gameName = gameData.get("name");
        Long userId = Long.parseLong(gameData.get("userId"));
        
        try {
            User user = userServiceProxy.getUserById(userId);
            Game game = gameService.createGame(gameName, user);
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating game: " + e.getMessage());
        }
    }
    
    @PostMapping("/{gameId}/join")
    public ResponseEntity<?> joinGame(@PathVariable String gameId, @RequestBody Map<String, Long> userData) {
        Long userId = userData.get("userId");
        
        try {
            User user = userServiceProxy.getUserById(userId);
            Game game = gameService.addPlayerToGame(gameId, user);
            return ResponseEntity.ok(game);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error joining game: " + e.getMessage());
        }
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Game>> getActiveGames() {
        List<Game> games = gameService.getActiveGames();
        return ResponseEntity.ok(games);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserGames(@PathVariable Long userId) {
        try {
            User user = userServiceProxy.getUserById(userId);
            List<Game> games = gameService.getPlayerGames(user);
            return ResponseEntity.ok(games);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/{gameId}/end")
    public ResponseEntity<?> endGame(@PathVariable String gameId, @RequestBody Map<String, Object> endData) {
        try {
            Long winnerUserId = Long.parseLong(endData.get("winnerUserId").toString());
            
            Game game = gameService.endGame(gameId, winnerUserId);
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error ending game: " + e.getMessage());
        }
    }
}