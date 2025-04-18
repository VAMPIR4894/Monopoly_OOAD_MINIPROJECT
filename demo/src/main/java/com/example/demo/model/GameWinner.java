package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_winners")
public class GameWinner {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
    
    @ManyToOne
    @JoinColumn(name = "winner_id", nullable = false)
    private User winner;
    
    private LocalDateTime winTime;
    
    // Default constructor
    public GameWinner() {
        this.winTime = LocalDateTime.now();
    }
    
    // Constructor with game and winner
    public GameWinner(Game game, User winner) {
        this();
        this.game = game;
        this.winner = winner;
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Game getGame() {
        return game;
    }
    
    public void setGame(Game game) {
        this.game = game;
    }
    
    public User getWinner() {
        return winner;
    }
    
    public void setWinner(User winner) {
        this.winner = winner;
    }
    
    public LocalDateTime getWinTime() {
        return winTime;
    }
    
    public void setWinTime(LocalDateTime winTime) {
        this.winTime = winTime;
    }
} 