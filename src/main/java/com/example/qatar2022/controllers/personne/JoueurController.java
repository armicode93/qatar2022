package com.example.qatar2022.controllers.personne;

import com.example.qatar2022.entities.*;
import com.example.qatar2022.entities.personne.Joueur;
import com.example.qatar2022.service.EquipeService;
// import com.example.qatar2022.service.ImageService;
import com.example.qatar2022.service.PartieService;
import com.example.qatar2022.service.PosteService;
import com.example.qatar2022.service.personne.JoueurService;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "*")
public class JoueurController {

  private final JoueurService joueurService;
  private final EquipeService equipeService;
  private final PosteService posteService;

  private final PartieService partieService;

  public JoueurController(
      JoueurService joueurService,
      EquipeService equipeService,
      PosteService posteService,
      PartieService partieService) {
    this.joueurService = joueurService;
    this.equipeService = equipeService;
    this.posteService = posteService;

    this.partieService = partieService;
  }

  @GetMapping("/addJoueur/{idEquipe}")
  public String joueurFormAdd(Model model, @PathVariable("idEquipe") Long idEquipe) {

    Equipe equipe = equipeService.getEquipeById(idEquipe);
    List<Poste> postes = posteService.getAllPoste();

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
      HttpSession session,


      ModelMap model) {

    if (result.hasErrors()) {
      return "joueur/add";
    }
    Equipe equipee = equipeService.getEquipeById(idEquipe);

    joueur.setNom(joueur.getNom());
    joueur.setPrenom(joueur.getPrenom());
    joueur.setBlessure(joueur.getBlessure());
    joueur.setEquipe(equipee);

    joueurService.addJoueur(joueur);




    model.addAttribute("joueur", joueur);
    model.addAttribute("idEquipe", idEquipe);

    return "redirect:/";
  }

  @GetMapping("/joueurEq1/{idPartie}")
  public String joueurIndexEq1(
      HttpSession session, Model model, @PathVariable("idPartie") Long idPartie) {

    Equipe equipe = partieService.getEq1ByPartieId(idPartie);
    List<Joueur> joueurs = equipe.getJoueur();
    List<JoueurPostes> joueursAndPostes = posteService.getJoueursAndPostesByPartieEq1(idPartie);

    model.addAttribute("equipe", equipe);
    session.setAttribute("idPartie", idPartie);

    model.addAttribute("joueursAndPostes", joueursAndPostes);

    model.addAttribute("joueurs", joueurs);

    return "joueur/formationEq1";
  }

  @GetMapping("/addPosteEq1/{idJoueur}")
  public String posteIndexEq1(
      HttpSession session, Model model, @PathVariable("idJoueur") Long idJoueur) {

    Joueur joueur = joueurService.getJoueurById(idJoueur);

    // Recuperer idPartie de cette session
    Long idPartie = (Long) session.getAttribute("idPartie");

    model.addAttribute("joueur", joueur);

    model.addAttribute("idPartie", idPartie);
    model.addAttribute("poste", new Poste());

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
    Equipe equipe = partieService.getEq1ByPartieId(idPartie);
    List<JoueurPostes> joueursAndPostes = posteService.getJoueursAndPostesByPartieEq2(idPartie);

    poste.setJoueur(joueur);
    poste.setPartie(partie);
    poste.setNomPoste(poste.getNomPoste());

    posteService.addPoste(poste);

    model.addAttribute("poste", poste);
    model.addAttribute("partie", partie);
    model.addAttribute("joueursAndPostes", joueursAndPostes);
    model.addAttribute("equipe", equipe);

    return "partie/show";
  }

  @GetMapping("/joueurEq2/{idPartie}")
  public String joueurIndexEq2(
      HttpSession session, Model model, @PathVariable("idPartie") Long idPartie) {

    Equipe equipe = partieService.getEq2ByPartieId(idPartie);
    List<Joueur> joueurs = equipe.getJoueur();
    List<JoueurPostes> joueursAndPostes = posteService.getJoueursAndPostesByPartieEq2(idPartie);

    model.addAttribute("equipe", equipe);
    session.setAttribute("idPartie", idPartie);

    model.addAttribute("joueursAndPostes", joueursAndPostes);

    model.addAttribute("joueurs", joueurs);

    return "joueur/formationEq2";
  }

  @GetMapping("/addPosteEq2/{idJoueur}")
  public String posteIndexEq2(
      Model model, @PathVariable("idJoueur") Long idJoueur, HttpSession session) {

    Joueur joueur = joueurService.getJoueurById(idJoueur);
    Long idPartie = (Long) session.getAttribute("idPartie");

    model.addAttribute("joueur", joueur);

    model.addAttribute("idPartie", idPartie);
    model.addAttribute("poste", new Poste());

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
    Equipe equipe = partieService.getEq1ByPartieId(idPartie);

    poste.setJoueur(joueur);
    poste.setPartie(partie);
    poste.setNomPoste(poste.getNomPoste());

    posteService.addPoste(poste);

    model.addAttribute("poste", poste);
    model.addAttribute("equipe", equipe);
    model.addAttribute("partie", partie);

    return "partie/show";
  }

  @DeleteMapping("/deletePosteEq1/{idPoste}")
  public String deletePosteEq1(
      @PathVariable("idPoste") Long idPoste, HttpSession session, Model model) {

    Poste existing = posteService.getPosteByid(idPoste);
    Long idPartie = (Long) session.getAttribute("idPartie");
    Equipe equipe = partieService.getEq1ByPartieId(idPartie);
    List<JoueurPostes> joueursAndPostes = posteService.getJoueursAndPostesByPartieEq1(idPartie);


      posteService.deletePoste(idPoste);

    Partie partie = partieService.getPartieById(idPartie);
    model.addAttribute("partie", partie);
    model.addAttribute("joueursAndPostes", joueursAndPostes);
    model.addAttribute("equipe", equipe);


    return "partie/show";
  }

  @DeleteMapping("/deletePosteEq2/{idPoste}")
  public String deletePosteEq2(
      @PathVariable("idPoste") Long idPoste, HttpSession session, Model model) {

    Poste existing = posteService.getPosteByid(idPoste);
    Long idPartie = (Long) session.getAttribute("idPartie");
    Equipe equipe = partieService.getEq2ByPartieId(idPartie);
    List<JoueurPostes> joueursAndPostes = posteService.getJoueursAndPostesByPartieEq2(idPartie);





      posteService.deletePoste(idPoste);

    Partie partie = partieService.getPartieById(idPartie);
    model.addAttribute("partie", partie);
    model.addAttribute("joueursAndPostes", joueursAndPostes);
    model.addAttribute("equipe", equipe);


    return "partie/show";
  }

  @GetMapping("/changerStatutEq1/{idJoueur}")
  public String changerStatutEq1(
      @PathVariable("idJoueur") Long idJoueur, HttpSession session, Model model) {

    Long idPartie = (Long) session.getAttribute("idPartie");

    Equipe equipe = partieService.getEq1ByPartieId(idPartie);
    List<JoueurPostes> joueursAndPostes = posteService.getJoueursAndPostesByPartieEq1(idPartie);

    Joueur joueur = joueurService.getJoueurById(idJoueur);
    Partie partie = partieService.getPartieById(idPartie);

    // invertir etat blessure ,if false to be true,if true will be false
    joueur.setBlessure(!joueur.getBlessure());

    joueurService.updateJoueur(idJoueur, joueur);

    List<Joueur> joueurs = equipe.getJoueur();

    model.addAttribute("joueursAndPostes", joueursAndPostes);

    model.addAttribute("joueurs", joueurs);

    model.addAttribute("equipe", equipe);

    model.addAttribute("partie", partie);

    return "joueur/formationEq1";
  }

  @GetMapping("/changerStatutEq2/{idJoueur}")
  public String changerStatutEq2(
      @PathVariable("idJoueur") Long idJoueur, HttpSession session, Model model) {

    Long idPartie = (Long) session.getAttribute("idPartie");

    Equipe equipe = partieService.getEq1ByPartieId(idPartie);
    List<JoueurPostes> joueursAndPostes = posteService.getJoueursAndPostesByPartieEq2(idPartie);

    Joueur joueur = joueurService.getJoueurById(idJoueur);
    Partie partie = partieService.getPartieById(idPartie);

    // invertir etat blessure ,if false to be true,if true will be false
    joueur.setBlessure(!joueur.getBlessure());

    joueurService.updateJoueur(idJoueur, joueur);

    List<Joueur> joueurs = equipe.getJoueur();

    model.addAttribute("joueursAndPostes", joueursAndPostes);

    model.addAttribute("joueurs", joueurs);

    model.addAttribute("equipe", equipe);

    model.addAttribute("partie", partie);

    return "joueur/formationEq2";
  }
}
