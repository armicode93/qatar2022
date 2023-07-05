package com.example.qatar2022.controllers;


import com.example.qatar2022.entities.*;
import com.example.qatar2022.entities.personne.Joueur;
import com.example.qatar2022.entities.personne.Staff;
import com.example.qatar2022.service.*;
import com.example.qatar2022.service.personne.JoueurService;
import com.example.qatar2022.service.personne.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@Controller
@RequestMapping("/v1/partie")
@CrossOrigin(origins = "*")
public class PartieController {

    private final PartieService partieService;
    private final ImageService imageService;
    private final EquipeService equipeService;
    private final StadeService stadeService;
    private final TourService tourService;
    private final UserServiceImpl userServiceImpl;
    private final StaffService staffService;


    private final JoueurService joueurService;

    public PartieController(PartieService partieService, ImageService imageService, EquipeService equipeService, StadeService stadeService, TourService tourService, UserServiceImpl userServiceImpl, StaffService staffService, JoueurService joueurService) {
        this.partieService = partieService;
        this.imageService = imageService;
        this.equipeService = equipeService;
        this.stadeService = stadeService;
        this.tourService = tourService;
        this.userServiceImpl = userServiceImpl;
        this.staffService = staffService;
        this.joueurService = joueurService;
    }


    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "0") int
            page, @RequestParam(defaultValue = "0") int size, @RequestParam(defaultValue = "title") String sortBy) {

        // <Partie> parties = partieService.getPartiteByTurno();
        List<Image> images = imageService.getAllImage();
        //   List<Tour> tours = tourService.getallTour();

        List<Partie> parties = partieService.getAllPartie();


       /* Map<Tour, List<P
       artie>> partitePerTurno = new LinkedHashMap<>(); // mappa per associare ad ogni turno l'elenco delle partite
        for (Tour tour : tours) {
            List<Partie> partite = partieService.getPartiteByTurno(tour); // recupera le partite del turno corrente dal database

            partitePerTurno.put(tour, partite); // aggiunge il turno e l'elenco delle partite alla mappa
        }

        */
        //model.addAttribute("partitePerTurno", partitePerTurno);

        model.addAttribute("parties", parties);
        // model.addAttribute("tours", tours);
        model.addAttribute("images", images);
        model.addAttribute("title", "Liste des matches");
        // recupera tutti i turni

        return "index";

    }


    @GetMapping("/show/{idPartie}")
    public String show(Model model, @PathVariable(name = "idPartie") Long idPartie) {

        Partie partie = partieService.getPartieById(idPartie);

             //String dateTimeConv = partieService.formatDateTime(partie.getDateTime());
        model.addAttribute("partie", partie);



         /*model.addAttribute("image1",partie.getEq1().getDrapeau().getId());
         model.addAttribute("image2", partie.getEq2().getDrapeau().getId());

          */


        model.addAttribute("title", "Detail Partie");
        //model.addAttribute("dateTimeConv", dateTimeConv);


        return "partie/show";
    }


    @GetMapping("/add")
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


    @GetMapping("/showJoueurByPartie/{idPartie}")
    public String showJoueurByPartie (Model model, @PathVariable(name="idPartie")Long idPartie)
    {
        Partie partie =  partieService.getPartieById(idPartie);


        // ho utilizzato una list perche prendo tanti giocatori in conto, cioe una lista
        List <Joueur> eq1Joueurs = partie.getEq1().getJoueur();
        List <Joueur> eq2Joueurs = partie.getEq2().getJoueur();

        List<Staff> eq1Staff = staffService.getStaffByEquipe(partie.getEq1());
        List<Staff> eq2Staff = staffService.getStaffByEquipe(partie.getEq2());




        model.addAttribute("partie",partie);
        model.addAttribute("eq1Joueurs",eq1Joueurs);
        model.addAttribute("eq2Joueurs",eq2Joueurs);
        model.addAttribute("eq1Staff",eq1Staff);
        model.addAttribute("eq2Staff",eq2Staff);

        model.addAttribute("joueurs", joueurService.getAllJoueur());






        return "partie/showJoueurByPartie";
    }




    @PostMapping("/add")
    public String partieSubmitAdd(@ModelAttribute("partie") Partie partie, @ModelAttribute("equipe") Equipe equipe, @ModelAttribute("stade") Stade stade, @ModelAttribute("tour") Tour tour, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "partie/add";
        }
        partie.setEq1(partie.getEq1());
        partie.setEq2(partie.getEq2());
        partie.setStade(partie.getStade());

        partie.setTour(partie.getTour());
        //partie.setScoreEq1(partie.getScoreEq1());
        //partie.setScoreEq2(partie.getScoreEq2());


        partie.setDateTime(partie.getDateTime());
        partie.setArbitre_principal(partie.getArbitre_principal());
        partie.setTotalTime(partie.getTotalTime());
        partie.setProlongation(partie.getProlongation());
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
    public String deletePartie(@PathVariable("idPartie")Long idPartie,Model model)
    {
        Partie exists = partieService.getPartieById(idPartie);
        if(exists != null)
        {
            if(partieService.hasReservationsForPartie(idPartie))
            {
                model.addAttribute("error","Cannot delete we have reservation");
                return "partie/deleteError";
            }
            else{
                partieService.deletePartie(idPartie);
            }

        }



          return "redirect:/";


    }



    @GetMapping("/editResult/{idPartie}")
    public String editResult(Model model, @PathVariable(name = "idPartie") String idPartie) {
        Partie partie = partieService.getPartieByIdPartie(idPartie);

        model.addAttribute("partie", partie);
        model.addAttribute("title", "");

        return "partie/editResultForm";


    }

    //importante Binding result deve essere subito dopo il parametro con cui facciamo il Valid, in modo che spring possa popolare l'oggetto
    @PostMapping("/editResult/{idPartie}")
    public String editResultSubmit(@Valid @ModelAttribute("partie") Partie partie, BindingResult result,@RequestParam("scoreEq1") int scoreEq1,
                                   @RequestParam("scoreEq2") int scoreEq2,@RequestParam("prolongation") String prolongation,@RequestParam("totalTime") String totalTime, @PathVariable("idPartie") Long idPartie, Model model) {

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
        partieService.updatePartie(idPartie, scoreEq1, scoreEq2, prolongation,totalTime);

        model.addAttribute("partie", partie);

// Crea una nuova Partie per il successivo giro del torneo utilizzando il tour recuperato

        return "redirect:/";


    }
    @GetMapping("/edit/{idPartie}")
    public String edit(Model model, @PathVariable(name = "idPartie") String idPartie) {
        Partie partie = partieService.getPartieByIdPartie(idPartie);
        List<Stade> stades = stadeService.getAllStade();
        List<Tour> tours = tourService.getallTour();
        List<Equipe>equipes = equipeService.getAllEquipe();

        model.addAttribute("partie", partie);
        model.addAttribute("stades", stades);
        model.addAttribute("tours",tours);
        model.addAttribute("equipes",equipes);
        model.addAttribute("title", "");

        return "partie/edit";


    }

    @PostMapping("/edit/{idPartie}")
    public String edit(@Valid @ModelAttribute("partie") Partie partie, BindingResult result, @PathVariable("idPartie") Long idPartie,
                       @RequestParam("stade") Stade stade, @RequestParam("dateTime") String dateTime,@RequestParam("arbitre_principal") String arbitre_principal,@RequestParam("prix") BigDecimal prix, Model model) {



        Partie existing = partieService.getPartieById(idPartie);

        if (existing == null) {
            return "redirect:/";
        }


       LocalDateTime dateTimeString = partieService.convertStringToLocalDateTime(dateTime);

// Aggiorna la Partie esistente con i nuovi punteggi
        partieService.editPartie(idPartie,stade,dateTimeString,arbitre_principal,prix);



        model.addAttribute("partie", partie);


        return "redirect:/";


    }



}
