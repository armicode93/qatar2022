package com.example.qatar2022.service.person;


import com.example.qatar2022.entities.person.Joueur;
import com.example.qatar2022.repository.person.JoueurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JoueurService {

    @Autowired
    private JoueurRepository joueurRepository;

    public List<Joueur> getAllJoueur()
    {
        List<Joueur> joueur = new ArrayList<>();
        joueurRepository.findAll().forEach(joueur::add);
        return joueur;
    }
    public void addJoueur(Joueur joueur)
    {
        joueurRepository.save(joueur);
    }
    public  void updateJoueur(Long cin, Joueur joueur)
    {
        joueurRepository.save(joueur);
    }
    public void deleteJoueur(Long cin)
    {
        joueurRepository.deleteById(cin);
    }
}
