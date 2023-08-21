package com.example.qatar2022.controllers.personne;

import com.example.qatar2022.entities.*;
import com.example.qatar2022.entities.personne.Joueur;
import com.example.qatar2022.service.EquipeService;
import com.example.qatar2022.service.ImageService;
import com.example.qatar2022.service.PosteService;
import com.example.qatar2022.service.personne.JoueurService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller

@CrossOrigin(origins = "*")
public class JoueurController {

  private final JoueurService joueurService;
  private final EquipeService equipeService;
  private final PosteService posteService;
  private final ImageService imageService;

  public JoueurController(JoueurService joueurService, EquipeService equipeService, PosteService posteService, ImageService imageService) {
    this.joueurService = joueurService;
    this.equipeService = equipeService;
    this.posteService = posteService;
    this.imageService = imageService;
  }

  @GetMapping("/addJoueur/{idEquipe}")

  public String joueurFormAdd( Model model, @PathVariable("idEquipe") Long idEquipe) {

    Equipe equipe = equipeService.getEquipeById(idEquipe);
    List <Poste> postes =  posteService.getAllPoste();



    model.addAttribute("equipe", equipe);
    model.addAttribute("joueur", new Joueur());
    model.addAttribute("postes", postes);

    return "joueur/add";
  }
  @PostMapping("/addJoueur/{idEquipe}")
  public String joueurSubmitAdd(
          @Valid @ModelAttribute("joueur") Joueur joueur,
          BindingResult result,
          @PathVariable(name = "idEquipe") Long idEquipe,
          ModelMap model) {

    if (result.hasErrors()) {
      return "joueur/add";
    }
    Equipe equipe = equipeService.getEquipeById(idEquipe);


    joueur.setNom(joueur.getNom());
    joueur.setPrenom(joueur.getPrenom());
    joueur.setBlessure(joueur.getBlessure());
    joueur.setEquipe(equipe);


   joueurService.addJoueur(joueur);





    model.addAttribute("joueur", joueur);
    model.addAttribute("equipe", equipe.getIdEquipe());



    return "redirect:/";
  }

  @GetMapping("/joueurEq1/{idEquipe}")
  public String joueurIndexEq1( Model model, @PathVariable("idEquipe") Long idEquipe) {
    List<Image> images = imageService.getAllImage();
    Equipe equipe = equipeService.getEquipeById(idEquipe);
    List <Joueur> joueurs = joueurService.getAllJoueurByEquipe(idEquipe);



    model.addAttribute("equipe", equipe);
    model.addAttribute("images", images);
    model.addAttribute("joueurs", joueurs);




    return "joueur/formationEq1";
  }

  @GetMapping("/addPosteEq1/{idJoueur}")
  public String posteIndexEq1(Model model) {




    model.addAttribute("poste",new Poste());



    return "joueur/adRoleEq1";
  }
  @PostMapping("/addPosteEq1/{idJoueur}")
  public String posteEq1SubmitAdd(
          @Valid @ModelAttribute("poste") Poste poste,
          BindingResult result,
          @ModelAttribute("partie") Partie partie ,
          @PathVariable(name = "idJoueur") Long idJoueur,
          ModelMap model) {

    if (result.hasErrors()) {
      return "joueur/addRoleEq1";
    }



    Joueur joueur = joueurService.getJoueurById(idJoueur);
    //Partie partie = partie

            poste.setJoueur(joueur);
    poste.setPartie(poste.getPartie());
    poste.setNomPoste(poste.getNomPoste());
    // Imposta gli altri campi del poste

    posteService.addPoste(poste);

    model.addAttribute("poste", poste);



    return "joueur/formationEq1";
  }

  @GetMapping("/joueurEq2/{idEquipe}")
  public String joueurIndexEq2( Model model, @PathVariable("idEquipe") Long idEquipe) {
    List<Image> images = imageService.getAllImage();
    Equipe equipe = equipeService.getEquipeById(idEquipe);
    List <Joueur> joueurs = joueurService.getAllJoueurByEquipe(idEquipe);



    model.addAttribute("equipe", equipe);
    model.addAttribute("images", images);
    model.addAttribute("joueurs", joueurs);




    return "joueur/formationEq2";
  }

  @GetMapping("/addPosteEq2/{idJoueur}")
  public String posteIndexEq2(Model model) {




    model.addAttribute("poste",new Poste());



    return "joueur/addRoleEq2";
  }
  @PostMapping("/addPosteEq2/{idJoueur}")
  public String posteEq2SubmitAdd(
          @Valid @ModelAttribute("poste") Poste poste,
          BindingResult result,
          @ModelAttribute("partie") Partie partie ,
          @PathVariable(name = "idJoueur") Long idJoueur,
          ModelMap model) {

    if (result.hasErrors()) {
      return "joueur/addRoleEq1";
    }



    Joueur joueur = joueurService.getJoueurById(idJoueur);
    //Partie partie = partie

    poste.setJoueur(joueur);
    poste.setPartie(poste.getPartie());
    poste.setNomPoste(poste.getNomPoste());
    // Imposta gli altri campi del poste

    posteService.addPoste(poste);

    model.addAttribute("poste", poste);



    return "joueur/formationEq2";
  }
}
