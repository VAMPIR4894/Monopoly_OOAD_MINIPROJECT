package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.model.Game;
import com.example.demo.model.GameWinner;
import com.example.demo.model.User;
import com.example.demo.repository.GameWinnerRepository;

@Service
public class GameWinnerService {
    
    private static final Logger logger = LoggerFactory.getLogger(GameWinnerService.class);

    @Autowired
    private GameWinnerRepository gameWinnerRepository;

    @Transactional
    public GameWinner recordWinner(Game game, User winner) {
        try {
            logger.info("Recording winner for game: {} with winner: {}", game.getId(), winner.getId());
            GameWinner gameWinner = new GameWinner(game, winner);
            GameWinner savedWinner = gameWinnerRepository.save(gameWinner);
            logger.info("Successfully recorded winner with ID: {}", savedWinner.getId());
            return savedWinner;
        } catch (Exception e) {
            logger.error("Error recording winner for game: {} with winner: {}", game.getId(), winner.getId(), e);
            throw e;
        }
    }

    public GameWinner getWinnerByGame(Game game) {
        try {
            logger.info("Fetching winner for game: {}", game.getId());
            return gameWinnerRepository.findAll().stream()
                    .filter(gw -> gw.getGame().getId().equals(game.getId()))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            logger.error("Error fetching winner for game: {}", game.getId(), e);
            throw e;
        }
    }
} 