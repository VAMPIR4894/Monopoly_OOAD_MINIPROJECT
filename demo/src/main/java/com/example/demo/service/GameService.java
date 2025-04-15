package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Game;
import com.example.demo.model.User;
import com.example.demo.repository.GameRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;
    
    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new game with the given name and first player
     */
    @Transactional
    public Game createGame(String gameName, User firstPlayer) {
        Game game = new Game(gameName);
        game.addPlayer(firstPlayer);
        return gameRepository.save(game);
    }
    
    /**
     * Adds a player to an existing game
     */
    @Transactional
    public Game addPlayerToGame(String gameId, User player) {
        Optional<Game> gameOpt = gameRepository.findById(gameId);
        if (gameOpt.isEmpty()) {
            throw new IllegalArgumentException("Game not found");
        }
        
        Game game = gameOpt.get();
        if (!"ACTIVE".equals(game.getStatus())) {
            throw new IllegalArgumentException("Cannot join a game that is not active");
        }
        
        game.addPlayer(player);
        return gameRepository.save(game);
    }
    
    /**
     * Gets all active games
     */
    public List<Game> getActiveGames() {
        return gameRepository.findByStatus("ACTIVE");
    }
    
    /**
     * Gets all games for a specific player
     */
    public List<Game> getPlayerGames(User player) {
        return gameRepository.findByPlayerAndStatus(player, "ACTIVE");
    }
    
    /**
     * Ends a game
     */
    @Transactional
    public Game endGame(String gameId, Long winnerUserId) {
        Game game = gameRepository.findById(gameId)
            .orElseThrow(() -> new IllegalArgumentException("Game not found"));
        
        User winner = userRepository.findById(winnerUserId)
            .orElseThrow(() -> new IllegalArgumentException("Winner user not found"));
        
        game.endGame();
        return gameRepository.save(game);
    }
}