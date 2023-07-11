package com.example.qatar2022.repository.personne;

import com.example.qatar2022.entities.personne.Joueur;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoueurRepository extends JpaRepository<Joueur, Long> {

  List<Joueur> findAllByEquipe_IdEquipe(Long idEquipe);

  // List<Joueur> findJoueurByPartieAndEquipe(Long idPartie, Long idEquipe);

  // List<Joueur> findJoueurByPartie(Long idPartie);

}
