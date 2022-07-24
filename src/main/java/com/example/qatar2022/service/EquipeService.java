package com.example.qatar2022.service;


import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;

    public List<Equipe> getAllEquipe()
    {
        List<Equipe> equipe = new ArrayList<>();
        equipeRepository.findAll().forEach(equipe::add);
        return  equipe;

    }

    public void addEquipe(Equipe equipe)
    {
        equipeRepository.save(equipe);
    }
    public void updateEquipe(Long idEquipe, Equipe equipe)
    {
        equipeRepository.save(equipe);
    }
    public void deleteEquipe(Long idEquipe)
    {
        equipeRepository.deleteById(idEquipe);
    }
}
