package com.example.qatar2022.service;

import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.repository.PartieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartieService {

    @Autowired
    private PartieRepository partieRepository ;

    public List<Partie> getAllParties()
    {
        List<Partie> parties = new ArrayList<>();

        partieRepository.findAll().forEach(parties::add);

        return parties;
    }

    public Partie getPartie(String idPartie)
    {
        int indice = Integer.parseInt(idPartie);

        return partieRepository.findById(indice);

    }
    public void addPartie(Partie partie)
    {
        partieRepository.save(partie);
    }

    public void updatePartie(Long idPartie, Partie partie)
    {
        partieRepository.save(partie);
    }

    public void deletePartie(Long idPartie)
    {
        partieRepository.deleteById(idPartie);
    }
}
