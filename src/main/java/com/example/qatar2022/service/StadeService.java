package com.example.qatar2022.service;

import com.example.qatar2022.entities.Stade;
import com.example.qatar2022.repository.StadeRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StadeService {

  private final StadeRepository stadeRepository;

  @Autowired
  public StadeService(StadeRepository stadeRepository) {
    this.stadeRepository = stadeRepository;
  }

  public List<Stade> getAllStade() {
    List<Stade> stades = new ArrayList<>();

    stadeRepository.findAll().forEach(stades::add);
    return stades;
  }

  public Stade getStadeById(Long idStade) {
    return stadeRepository.findById(idStade).orElse(null);
  }

  public void addStade(Stade stade) {
    stadeRepository.save(stade);
  }

  public void deleteStade(Long idStade) {
    boolean exists = stadeRepository.existsById(idStade);
    if (!exists) {
      throw new IllegalStateException("Not exists");
    }
    stadeRepository.deleteById(idStade);
  }

  public Stade updateStade(Long idStade, Stade stade) {
    stadeRepository.save(stade);
    return stade;
  }
}
