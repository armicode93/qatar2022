package com.example.qatar2022.controllers;

import com.example.qatar2022.entities.*;
import com.example.qatar2022.entities.personne.Joueur;
import com.example.qatar2022.entities.personne.Staff;
import com.example.qatar2022.service.*;
import com.example.qatar2022.service.personne.JoueurService;
import com.example.qatar2022.service.personne.StaffService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller

@CrossOrigin(origins = "*")
public class PartieController {

  private final PartieService partieService;
  //private final ImageService imageService;
  private final EquipeService equipeService;
  private final StadeService stadeService;
  private final TourService tourService;
  private final UserServiceImpl userServiceImpl;
  private final StaffService staffService;

  private final JoueurService joueurService;
  private boolean isQuartsCreated = false;
  private boolean isDemiFinalCreated = false;
  private boolean isFinalCreated = false;

  public PartieController(
      PartieService partieService,
     // ImageService imageService,
      EquipeService equipeService,
      StadeService stadeService,
      TourService tourService,
      UserServiceImpl userServiceImpl,
      StaffService staffService,
      JoueurService joueurService) {
    this.partieService = partieService;
   // this.imageService = imageService;
    this.equipeService = equipeService;
    this.stadeService = stadeService;
    this.tourService = tourService;
    this.userServiceImpl = userServiceImpl;
    this.staffService = staffService;
    this.joueurService = joueurService;
  }

  @GetMapping("/partie/index")
  public String index(
          @RequestParam(name="tourId", required = false) Long idTour,
          @RequestParam(name= "date", required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate date,
          Model model) {

  List<Tour> tours = tourService.getallTour();

    List<Partie> parties;

    if(idTour != null) {

        parties = partieService.getPartieByTour(idTour);
      }
    else if ( date != null)
    {
       parties = partieService.getPartiesByDate(date);
    }
    else {
      parties = partieService.getAllPartie();
    }

    model.addAttribute("parties", parties);
    model.addAttribute("tours", tours);
    //    //   );
    model.addAttribute("title", "Liste des matches");
    // recupera tutti i turni

    return "partie/partieIndex";
  }

  @GetMapping("/partie/{idPartie}")
  public String show(Model model, @PathVariable(name = "idPartie") Long idPartie) {

    List <Equipe> equipe = equipeService.getAllEquipe();
    Partie partie = partieService.getPartieById(idPartie);

    model.addAttribute("partie", partie);
    model.addAttribute("equipe",equipe);
    model.addAttribute("title", "Detail Partie");

    return "partie/show";
  }

  @GetMapping("/partie")
  public String partieFormAdd(Model model) {
    List<Equipe> equipes = equipeService.getAllEquipe();
    List<Stade> stades = stadeService.getAllStade();
    List<Tour> tours = tourService.getallTour();

    model.addAttribute("stades", stades);
    model.addAttribute("equipes", equipes);
    model.addAttribute("tours", tours);
    model.addAttribute(new Partie());

    return "partie/add";
  }



  @PostMapping("/partie")
  public String partieSubmitAdd(
      @Valid @ModelAttribute("partie") Partie partie,
      BindingResult result,
      @ModelAttribute("equipe") Equipe equipe,
      @ModelAttribute("stade") Stade stade,
      @ModelAttribute("tour") Tour tour,
      ModelMap model) {
    if (result.hasErrors()) {
      return "partie/add";
    }

    LocalDateTime convertedDateTime =
        partieService.convertStringToLocalDateTime(partie.getDateTimeAsString());

    partie.setEq1(partie.getEq1());
    partie.setEq2(partie.getEq2());
    partie.setStade(partie.getStade());

    partie.setTour(partie.getTour());

    partie.setDateTime(convertedDateTime);
    partie.setArbitre_principal(partie.getArbitre_principal());
    partie.setPrix(partie.getPrix());

    partieService.addPartie(partie);

    model.addAttribute("eq1", equipe.getIdEquipe());
    model.addAttribute("eq2", equipe.getIdEquipe());
    model.addAttribute("stade", stade.getIdStade());
    model.addAttribute("tour", tour.getIdTour());

    model.addAttribute("partie", "");

    return "redirect:/";
  }

  @DeleteMapping(path = "/delete/{idPartie}")
  public String deletePartie(@PathVariable("idPartie") Long idPartie, Model model) {
    Partie exists = partieService.getPartieById(idPartie);
    if (exists != null) {
      if (partieService.hasReservationsForPartie(idPartie)) {
        model.addAttribute("error", "Cannot delete we have reservation");
        return "partie/deleteError";
      } else {
        partieService.deletePartie(idPartie);
      }
    }

    return "redirect:/partie/index";
  }

  @GetMapping("/editResult/{idPartie}")
  public String editResult(Model model, @PathVariable(name = "idPartie") String idPartie) {
    Partie partie = partieService.getPartieByIdPartie(idPartie);

    model.addAttribute("partie", partie);
    model.addAttribute("title", "");

    return "partie/editResultForm";
  }

  @PostMapping("/editResult/{idPartie}")
  public String editResultSubmit(
      @Valid @ModelAttribute("partie") Partie partie,
      BindingResult result,
      @RequestParam("scoreEq1") int scoreEq1,
      @RequestParam("scoreEq2") int scoreEq2,
      @RequestParam("prolongation") String prolongation,
      @RequestParam("totalTime") String totalTime,
      @PathVariable("idPartie") Long idPartie,
      Model model) {

    if (result.hasErrors()) {
      return "partie/editResultForm";
    }
    Partie existing = partieService.getPartieById(idPartie);

    if (existing == null) {
      return "redirect:/";
    }
    // Recupera il valore di tour dalla Partie esistente
    Tour tour = existing.getTour();
    // Aggiorna la Partie esistente con i nuovi punteggi
    partieService.updatePartie(idPartie, scoreEq1, scoreEq2, prolongation, totalTime);

    model.addAttribute("partie", partie);



    return "redirect:/partie/" + idPartie;
  }

  @GetMapping("/partie/edit/{idPartie}")
  public String edit(Model model, @PathVariable(name = "idPartie") String idPartie) {

    Partie partie = partieService.getPartieByIdPartie(idPartie);
    List<Stade> stades = stadeService.getAllStade();
    List<Tour> tours = tourService.getallTour();
    List<Equipe> equipes = equipeService.getAllEquipe();

    model.addAttribute("partie", partie);
    model.addAttribute("stades", stades);
    model.addAttribute("tours", tours);
    model.addAttribute("equipes", equipes);
    model.addAttribute("title", "");

    return "partie/edit";
  }

//@ConntrollerAdvice to handler all error together

  @PostMapping("partie/edit/{idPartie}")
  public String edit(
          @Valid @ModelAttribute("partie") Partie partie,
          BindingResult result,
          @PathVariable("idPartie") Long idPartie,
           @RequestParam(name = "stade", required = false) Stade stade,
          @RequestParam("dateTime") String dateTime,
          @RequestParam("arbitre_principal") String arbitre_principal,
          @RequestParam("prix") BigDecimal prix,

          Model model) {

    if (result.hasErrors()) {
      List<Stade> stades = stadeService.getAllStade();
      model.addAttribute("stades", stades);
      model.addAttribute("dateTime", dateTime);
      return "partie/edit";
    }




    LocalDateTime dateTimeString =
            partieService.convertStringToLocalDateTime(partie.getDateTimeAsString());

    partieService.editPartie(idPartie, stade, dateTimeString, arbitre_principal, prix);


    model.addAttribute("stade", stade);


    model.addAttribute("partie", partie);

    return "redirect:/partie/" + idPartie;
  }


  @GetMapping("/createParties")
  public String createParties( Model model) {

    List<Tour> tours = tourService.getallTour();

    model.addAttribute("tours",tours);

    return "partie/createParties";
  }

  @PostMapping("/createParties")
  public String createParties(
          @ModelAttribute("partie") Partie partie,
          Model model) {
    List<Tour> tours = tourService.getallTour();
    List<Equipe> equipes = equipeService.getAllEquipe();

    if (equipes == null || equipes.size() < 16 || !tours.isEmpty()) {
      return "partie/partieError";
    }
    else if (equipes.size() > 16)
    {
      return "partie/partieErrorPlus";
    }

    partieService.createInitialKnockoutMatches(equipes);


    model.addAttribute("partie", partie);

    return "redirect:/partie/index";

  }

  @PostMapping("/createNextParties")
  public String createNextParties(@ModelAttribute("partie")Partie partie,BindingResult result, @RequestParam("nomTour") String nomTour,Model model)
  {

    List<Partie> parties = partieService.getAllPartie();



  List<Equipe> currentTourWinners = partieService.calculateGroupWinners(parties, nomTour);

  if(result.hasErrors())
  {
    return "partie/partieAddError";
  }

  else if (currentTourWinners.isEmpty() ) {
      return "partie/partieAddError";
    }
    else if(currentTourWinners.size()==8 && isQuartsCreated ==false)
    {
      partieService.createQuarts(currentTourWinners);
      isQuartsCreated = true;
    }
    else if(currentTourWinners.size()==4 && isDemiFinalCreated==false  )
    {
      partieService.createDemiFinal(currentTourWinners);
      isDemiFinalCreated = true;
    }
    else if(currentTourWinners.size()==2 && isFinalCreated==false)
    {
      partieService.createFinal(currentTourWinners);
      isFinalCreated = true;

    }
    else  {
      return "partie/partieAddError";
    }




    model.addAttribute("partie", partie);

    return "redirect:/partie/index";



  }




}
