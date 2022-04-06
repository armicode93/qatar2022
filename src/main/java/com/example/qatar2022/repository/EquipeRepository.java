package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipeRepository extends JpaRepository<Equipe,Long> {

    Equipe findById(long idEquipe);
    List <Equipe> findByPays(String pays);




}
