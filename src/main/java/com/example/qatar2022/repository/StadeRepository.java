package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Stade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadeRepository extends JpaRepository<Stade, Long> {}
