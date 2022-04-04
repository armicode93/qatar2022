package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Equipe,Long> {

}
