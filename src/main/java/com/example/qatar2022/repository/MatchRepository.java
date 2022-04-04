package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Partie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Partie,Long> {
}
