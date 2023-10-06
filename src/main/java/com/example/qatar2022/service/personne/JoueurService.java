package com.example.qatar2022.service.personne;

import com.example.qatar2022.entities.personne.Joueur;
import com.example.qatar2022.repository.EquipeRepository;
import com.example.qatar2022.repository.personne.JoueurRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


@Service
public class JoueurService {

  private final JoueurRepository joueurRepository;
  private final EquipeRepository equipeRepository;

  public JoueurService(JoueurRepository joueurRepository, EquipeRepository equipeRepository) {
    this.joueurRepository = joueurRepository;
    this.equipeRepository = equipeRepository;
  }

  public List<Joueur> getAllJoueur() {
    List<Joueur> joueurs = new ArrayList<>();
    joueurRepository.findAll().forEach(joueurs::add);
    return joueurs;
  }

  public List<Joueur> getAllJoueurByEquipe(Long idEquipe) {

    return joueurRepository.findAllByEquipe_IdEquipe(idEquipe);
  }

  public Joueur getJoueurById(Long idJoueur) {
    return joueurRepository.findById(idJoueur).orElse(null);
  }

  public void addJoueur(Joueur joueur) {
    joueurRepository.save(joueur);
  }

  public Joueur updateJoueur(Long idJoueur, Joueur joueur) {

    joueurRepository.save(joueur);
    return joueur;
  }

  @Transactional
  public void deleteJoueur(Long idJoueur) {

    boolean exists = joueurRepository.existsById(idJoueur);

    if (!exists) {
      throw new IllegalStateException("Not exists");
    }

    joueurRepository.deleteByIdJoueur(idJoueur);
  }
}
