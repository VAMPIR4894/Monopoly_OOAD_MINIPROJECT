package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.GameWinner;

@Repository
public interface GameWinnerRepository extends JpaRepository<GameWinner, Long> {
    // Add custom query methods if needed
} 