package com.example.qatar2022.repository.person;

import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Spectateur;
import com.example.qatar2022.entities.person.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoueurRepository extends JpaRepository<Joueur,Long> {

    Joueur findByEquipe(Equipe equipe);

    List<Joueur> findByPoste(String poste);


}
