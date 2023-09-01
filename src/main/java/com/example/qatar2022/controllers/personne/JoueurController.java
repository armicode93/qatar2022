package com.example.qatar2022.controllers.personne;

import com.example.qatar2022.entities.*;
import com.example.qatar2022.entities.personne.Joueur;
import com.example.qatar2022.service.EquipeService;
//import com.example.qatar2022.service.ImageService;
import com.example.qatar2022.service.PartieService;
import com.example.qatar2022.service.PosteService;
import com.example.qatar2022.service.personne.JoueurService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller

@CrossOrigin(origins = "*")
public class JoueurController {

  private final JoueurService joueurService;
  private final EquipeService equipeService;
  private final PosteService posteService;
  //private final ImageService imageService;
  private final PartieService partieService;

  public JoueurController(JoueurService joueurService, EquipeService equipeService, PosteService posteService , PartieService partieService) {
    this.joueurService = joueurService;
    this.equipeService = equipeService;
    this.posteService = posteService;

    this.partieService = partieService;
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

  @GetMapping("/joueurEq1/{idPartie}")
  public String joueurIndexEq1(HttpSession session, Model model, @PathVariable("idPartie") Long idPartie) {
    //List<Image> images = imageService.getAllImage();
    Equipe equipe = partieService.getEq1ByPartieId(idPartie);
    List <Joueur> joueurs = equipe.getJoueur();
    List<JoueurPostes> joueursAndPostes = posteService.getJoueursAndPostesByPartieEq1(idPartie);





    model.addAttribute("equipe", equipe);
    session.setAttribute("idPartie",idPartie);
   // model.addAttribute("images", images);
    model.addAttribute("joueursAndPostes", joueursAndPostes);

    model.addAttribute("joueurs", joueurs);




    return "joueur/formationEq1";
  }

  @GetMapping("/addPosteEq1/{idJoueur}")
  public String posteIndexEq1(HttpSession session, Model model, @PathVariable("idJoueur") Long idJoueur) {


    Joueur joueur = joueurService.getJoueurById(idJoueur);

    // Recupera idPartie de cette session
    Long idPartie = (Long) session.getAttribute("idPartie");


    model.addAttribute("joueur",joueur);

    model.addAttribute("idPartie",idPartie);
    model.addAttribute("poste",new Poste());



    return "joueur/addPosteEq1";

  }

  @PostMapping("/addPosteEq1/{idJoueur}")
  public String posteEq1SubmitAdd(
          @Valid @ModelAttribute("poste") Poste poste,
          BindingResult result,

          @PathVariable(name = "idJoueur") Long idJoueur,

          HttpSession session,
          ModelMap model) {

    if (result.hasErrors()) {
      return "joueur/addPosteEq1";
    }



    Joueur joueur = joueurService.getJoueurById(idJoueur);
    Long idPartie = (Long) session.getAttribute("idPartie");
    Partie partie = partieService.getPartieById(idPartie);

    poste.setJoueur(joueur);
    poste.setPartie(partie);
    poste.setNomPoste(poste.getNomPoste());


    posteService.addPoste(poste);

    model.addAttribute("poste", poste);
    model.addAttribute("partie",partie);



    return "partie/show";
  }

  @GetMapping("/joueurEq2/{idPartie}")
  public String joueurIndexEq2(HttpSession session, Model model, @PathVariable("idPartie") Long idPartie) {
    //List<Image> images = imageService.getAllImage();
    Equipe equipe = partieService.getEq2ByPartieId(idPartie);
    List <Joueur> joueurs = equipe.getJoueur();
    List<JoueurPostes> joueursAndPostes = posteService.getJoueursAndPostesByPartieEq2(idPartie);



    model.addAttribute("equipe", equipe);
    session.setAttribute("idPartie",idPartie);
   // model.addAttribute("images", images);
    model.addAttribute("joueursAndPostes", joueursAndPostes);

    model.addAttribute("joueurs", joueurs);




    return "joueur/formationEq2";
  }

  @GetMapping("/addPosteEq2/{idJoueur}")
  public String posteIndexEq2(Model model,@PathVariable("idJoueur")Long idJoueur, HttpSession session) {


    Joueur joueur = joueurService.getJoueurById(idJoueur);
    Long idPartie = (Long) session.getAttribute("idPartie");


    model.addAttribute("joueur",joueur);

    model.addAttribute("idPartie",idPartie);
    model.addAttribute("poste",new Poste());



    return "joueur/addPosteEq2";
  }
  @PostMapping("/addPosteEq2/{idJoueur}")
  public String posteEq2SubmitAdd(
          @Valid @ModelAttribute("poste") Poste poste,
          BindingResult result,
          @PathVariable(name = "idJoueur") Long idJoueur,
          HttpSession session,
          ModelMap model) {

    if (result.hasErrors()) {
      return "addPosteEq2";
    }



    Joueur joueur = joueurService.getJoueurById(idJoueur);
    Long idPartie = (Long) session.getAttribute("idPartie");
    Partie partie = partieService.getPartieById(idPartie);

    poste.setJoueur(joueur);
    poste.setPartie(partie);
    poste.setNomPoste(poste.getNomPoste());


    posteService.addPoste(poste);

    model.addAttribute("poste", poste);
    model.addAttribute("partie",partie);




    return "partie/show";
  }
  @DeleteMapping("/deletePosteEq1/{idPoste}")
  public String deletePosteEq1(@PathVariable("idPoste") Long idPoste, HttpSession session,Model model ) {
    //List<Image> images = imageService.getAllImage();
    Poste existing = posteService.getPosteByid(idPoste);
    Long idPartie = (Long) session.getAttribute("idPartie");
    if (existing != null) {


      posteService.deletePoste(idPoste);
    }
    session.setAttribute("idPartie",idPartie);
   // model.addAttribute("images", images);
    return "redirect:/";
  }
  @DeleteMapping("/deletePosteEq2/{idPoste}")
  public String deletePosteEq2(@PathVariable("idPoste") Long idPoste) {

    Poste existing = posteService.getPosteByid(idPoste);


    if (existing != null) {


      posteService.deletePoste(idPoste);
    }

    return "partie/show";
  }


}
