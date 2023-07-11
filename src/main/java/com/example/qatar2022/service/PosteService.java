package com.example.qatar2022.service;

import com.example.qatar2022.entities.Poste;
import com.example.qatar2022.repository.PartieRepository;
import com.example.qatar2022.repository.PosteRepository;
import com.example.qatar2022.repository.personne.JoueurRepository;
import java.util.ArrayList;
import java.util.List;
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
}
