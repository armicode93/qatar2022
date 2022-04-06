package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.Stade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PartieRepository extends JpaRepository<Partie, Long> {

    Partie findById(long idPartie);

    List <Partie> findByDate(Date date);

    List <Partie> findByStade (Stade stade);

    List <Partie> findByEq1(Equipe equipe);
    List <Partie> findByEq2(Equipe equipe);



}
