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


    public List<Equipe> getAllEquipes()
    {
        List<Equipe> equipes = new ArrayList<>();

        equipeRepository.findAll().forEach(equipes::add);

        return equipes;
    }

    public Equipe getEquipe(String idEquipe)
    {
        int indice = Integer.parseInt(idEquipe);
        return equipeRepository.findById(indice);

    }
    public void addEquipe(Equipe equipe)
    {
        equipeRepository.save(equipe);

    }
    public void updateEquipe(Long idEquipe, Equipe equipe)
    {
        equipeRepository.save(equipe);
    }

    public void deleteEquipe (Long idEquipe)
    {
        equipeRepository.deleteById(idEquipe);
    }


}
