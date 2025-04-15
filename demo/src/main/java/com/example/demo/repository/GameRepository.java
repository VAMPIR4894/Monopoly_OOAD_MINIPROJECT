package com.example.demo.repository;

import com.example.demo.model.Game;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, String> {
    List<Game> findByStatus(String status);
    
    @Query("SELECT g FROM Game g JOIN g.players p WHERE p = :player AND g.status = :status")
    List<Game> findByPlayerAndStatus(@Param("player") User player, @Param("status") String status);
}