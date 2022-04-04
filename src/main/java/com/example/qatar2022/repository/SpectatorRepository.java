package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Spectateurs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpectatorRepository extends JpaRepository<Spectateurs,Long> {
}
