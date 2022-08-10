package com.example.qatar2022.service;


import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.repository.PartieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartieService {

    private final PartieRepository partieRepository;


    @Autowired
    public PartieService(PartieRepository partieRepository) {
        this.partieRepository = partieRepository;
    }


    public List<Partie> getAllPartie()
    {
        List <Partie> parties = new ArrayList<>();
        partieRepository.findAll().forEach(parties::add);
        return parties;
    }

    public Partie getPartieById(Long idPartie)
    {
        return partieRepository.findById(idPartie).orElse(null);
    }

    public void addPartie(Partie partie)
    {
        partieRepository.save(partie);
    }

    public void deletePartie(Long idPartie)
    {
        boolean exists = partieRepository.existsById(idPartie);
        if(!exists)
        {
            throw new IllegalStateException("Not exists");
        }
        partieRepository.deleteById(idPartie);
    }

    public Partie updatePartie(Long idPartie, Partie partie)
    {
        partieRepository.save(partie);
        return partie;
    }
}
