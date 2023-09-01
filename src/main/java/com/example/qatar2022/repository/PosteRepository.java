package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.Poste;
import com.example.qatar2022.entities.personne.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosteRepository extends JpaRepository<Poste, Long> {

    List<Poste> findPosteByPartieAndJoueur(Partie partie, Joueur joueur);

}
