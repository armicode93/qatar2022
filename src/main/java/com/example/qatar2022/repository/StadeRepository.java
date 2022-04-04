package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Image;
import com.example.qatar2022.entities.Spectateur;
import com.example.qatar2022.entities.Stade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StadeRepository extends JpaRepository<Stade,Long> {

    Stade findById(long idStade);

    List<Stade> findByNomStade(String nomStade);

    List<Stade> findByTown(String town);
}
