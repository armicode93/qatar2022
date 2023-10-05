package com.example.qatar2022.service;

import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.repository.EquipeRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipeService {

  private final EquipeRepository equipeRepository;

  @Autowired
  public EquipeService(EquipeRepository equipeRepository) {
    this.equipeRepository = equipeRepository;
  }

  public List<Equipe> getAllEquipe() {
    List<Equipe> equipes = new ArrayList<>();

    equipeRepository.findAll().forEach(equipes::add);

    return equipes;
  }

  public Equipe getEquipeById(Long idEquipe) {
    return equipeRepository.findById(idEquipe).orElse(null);
  }

  public void addEquipe(Equipe equipe) {

    equipe.setPays(equipe.getPays());
    equipe.setDrapeau(equipe.getDrapeau());

    equipeRepository.save(equipe);
  }

  public void deleteEquipe(Long idEquipe) {

    equipeRepository.deleteById(idEquipe);
  }

  public Equipe updateEquipe(Long idEquipe, Equipe equipe) {
    equipeRepository.save(equipe);

    return equipe;
  }
}
