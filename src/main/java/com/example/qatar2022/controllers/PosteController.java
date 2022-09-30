package com.example.qatar2022.controllers;

import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.Poste;
import com.example.qatar2022.entities.personne.Joueur;
import com.example.qatar2022.service.EquipeService;
import com.example.qatar2022.service.PartieService;
import com.example.qatar2022.service.PosteService;
import com.example.qatar2022.service.personne.JoueurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/v1/Poste")
@CrossOrigin(origins="*")
public class PosteController {

    private final PosteService posteService;

    private final JoueurService joueurService;

    private final PartieService partieService;

    private final EquipeService equipeService;

    public PosteController(PosteService posteService, JoueurService joueurService, PartieService partieService, EquipeService equipeService) {
        this.posteService = posteService;
        this.joueurService = joueurService;
        this.partieService = partieService;
        this.equipeService = equipeService;
    }


 /*  @GetMapping("/")
    public String index (Model model)
    {
        List<Poste> postes = posteService.getAllPoste();
        List<Joueur> joueurs = joueurService.getAllJoueur();

        model.addAttribute("poste",postes);
        model.addAttribute("joueur",joueurs);

        return "partie/showJoueurByPartie";
    }

  */








}



