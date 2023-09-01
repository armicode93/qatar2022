package com.example.qatar2022.service;

import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.JoueurPostes;
import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.Poste;
import com.example.qatar2022.entities.personne.Joueur;
import com.example.qatar2022.repository.PartieRepository;
import com.example.qatar2022.repository.PosteRepository;
import com.example.qatar2022.repository.personne.JoueurRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modelmapper.internal.Pair;
import org.springframework.stereotype.Service;

@Service
public class PosteService {

  private final PosteRepository posteRepository;
  private final JoueurRepository joueurRepository;
  private final PartieRepository partieRepository;

  public PosteService(
      PosteRepository posteRepository,
      JoueurRepository joueurRepository,
      PartieRepository partieRepository) {
    this.posteRepository = posteRepository;
    this.joueurRepository = joueurRepository;
    this.partieRepository = partieRepository;
  }

  public List<Poste> getAllPoste() {
    List<Poste> postes = new ArrayList<>();

    posteRepository.findAll().forEach(postes::add);

    return postes;
  }

  public Poste getPosteByid(Long idPoste) {
    return posteRepository.findById(idPoste).orElse(null);
  }

  public void addPoste(Poste poste) {
    posteRepository.save(poste);
  }

  public void deletePoste(Long idPoste) {
    posteRepository.deleteById(idPoste);
  }

  public void updatePoste(Long idPoste, Poste poste) {
    posteRepository.deleteById(idPoste);
  }

  public List<JoueurPostes> getJoueursAndPostesByPartieEq1(Long idPartie) {
    Partie partie = partieRepository.findById(idPartie).orElse(null);



    Equipe equipe1 = partie.getEq1();
    List<Joueur> joueursEq1 = equipe1.getJoueur();
    List<JoueurPostes> joueursAndPostes = new ArrayList<>();

    for (Joueur joueur : joueursEq1) {

      List<Poste> postes = posteRepository.findPosteByPartieAndJoueur(partie, joueur);
      joueursAndPostes.add(new JoueurPostes(joueur, postes));
    }

    return joueursAndPostes;
  }
  public List<JoueurPostes> getJoueursAndPostesByPartieEq2(Long idPartie) {
    Partie partie = partieRepository.findById(idPartie).orElse(null);



    Equipe equipe2 = partie.getEq2();
    List<Joueur> joueursEq2 = equipe2.getJoueur();
    List<JoueurPostes> joueursAndPostes = new ArrayList<>();

    for (Joueur joueur : joueursEq2) {

      List<Poste> postes = posteRepository.findPosteByPartieAndJoueur(partie, joueur);
      joueursAndPostes.add(new JoueurPostes(joueur, postes));
    }

    return joueursAndPostes;
  }
}
