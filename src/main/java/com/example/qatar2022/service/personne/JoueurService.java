package com.example.qatar2022.service.personne;

import com.example.qatar2022.entities.personne.Joueur;
import com.example.qatar2022.repository.personne.JoueurRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JoueurService {


    private final JoueurRepository joueurRepository;

    public JoueurService(JoueurRepository joueurRepository) {
        this.joueurRepository = joueurRepository;
    }

    public List<Joueur> getAllJoueur()
    {
        List<Joueur> joueur = new ArrayList<>();
        joueurRepository.findAll().forEach(joueur::add);
        return joueur;
    }
    public List<Joueur> getAllJoueurByEquipe(Long idEquipe)
    {

        return  joueurRepository.findAllByEquipe_IdEquipe(idEquipe);

    }

    public Joueur getJoueurById(Long cin)
    {
        return joueurRepository.findById(cin).orElse(null);
    }
    public void addJoueur(Joueur joueur)
    {
        joueurRepository.save(joueur);
    }
    public  Joueur updateJoueur(Long cin, Joueur joueur)
    {

        joueurRepository.save(joueur);
        return joueur;
    }
    public void deleteJoueur(Long cin)
    {

        boolean exists = joueurRepository.existsById(cin);

        if(!exists)
        {
            throw  new IllegalStateException("Not exists");
        }
        joueurRepository.deleteById(cin);
    }
}