package com.example.qatar2022.controllers;


import com.example.qatar2022.dto.ReservationDTO;
import com.example.qatar2022.entities.*;
import com.example.qatar2022.entities.personne.User;
import com.example.qatar2022.repository.personne.UserRepository;
import com.example.qatar2022.service.PartieService;
import com.example.qatar2022.service.ReservationService;
import com.example.qatar2022.service.StadeService;
import com.example.qatar2022.service.TicketService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller

// Rest controller invece ritorna il oggetto vero e proprio o testo
@RequestMapping("/reservation")
@CrossOrigin(origins = "*")
public class ReservationController {


    @Autowired
    private  final ReservationService reservationService;

    @Autowired
    private final StadeService stadeService;

    @Autowired
    private final TicketService ticketService;

    @Autowired
    private final PartieService partieService;

    private Partie partie;

    @Autowired
    private final UserRepository userRepository;

    private int nbr_places;


    @Autowired
    private ModelMapper modelMapper;

    public ReservationController(ReservationService reservationService, StadeService stadeService, TicketService ticketService, PartieService partieService, UserRepository userRepository) {
        this.reservationService = reservationService;
        this.stadeService = stadeService;
        this.ticketService = ticketService;
        this.partieService = partieService;
        this.userRepository = userRepository;

    }


    private ReservationDTO convertEntityToDto(Reservation reservation)
    {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO= modelMapper.map(reservation,ReservationDTO.class);
        return  reservationDTO;
    }
    private Reservation convertDtoToEntity(ReservationDTO reservationDTO)
    {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);

        Reservation reservation = new Reservation();
        reservation = modelMapper.map(reservationDTO, Reservation.class);
        return reservation;
    }



    @GetMapping("/index")
    public String index (Model model)
    {
      List<Reservation> reservations = reservationService.getAllReservation();
        // List<Partie> parties = partieService.getPartieByEquipe();
        List<Partie> parties = partieService.getAllPartie();
        reservations.stream().map(this::convertEntityToDto).collect(Collectors.toList());

        model.addAttribute("reservations",reservations);
        model.addAttribute("parties",parties);
        model.addAttribute("title","Liste des reservations");

        return "reservation/index";
    }








    @GetMapping("/{codeReservation}")
    public ResponseEntity findReservationById(@PathVariable(name = "codeReservation") Long codeReservation) {


        if (codeReservation == null) {
            return ResponseEntity.badRequest().body("Empty parametre");
        }

        Optional<Reservation> reservation = Optional.ofNullable(reservationService.getReservationById(codeReservation));


        if (reservation.isPresent()) {

            return ResponseEntity.ok(convertEntityToDto(reservationService.getReservationById(codeReservation)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/selectPartie/{idPartie}")
    public String selectPartiePlace(Model model, @PathVariable(name = "idPartie") String idPartie, @ModelAttribute("user") User user, @ModelAttribute("stade") Stade stade) {
        Partie partie = partieService.getPartieByIdPartie(idPartie);

        // I have to check if match has already been played
        if(partie.getScoreEq1()!= null && partie.getScoreEq2() != null )
        {
            model.addAttribute("title", "Match already played");
            return "reservation/matchPlayed";
        }
        if(partie.getPrix()== null )
        {
            model.addAttribute("title", "Match already played");
            return "reservation/matchNoReservable";
        }
        if(partie.getStade().getCapacite() == 0)
        {
            model.addAttribute("title","Match sold out");
                    return "reservation/MatchSoldOut";
        }
        model.addAttribute("partie", partie);
        model.addAttribute("user", user);


        model.addAttribute("reservation", new Reservation());
        model.addAttribute("title", "");
        return "reservation/indexReservPartie";
    }

    @PostMapping("/reservationPartie/{idPartie}")
    public String selectPartiePlacetSubmit(@ModelAttribute("reservation") Reservation reservation, @ModelAttribute("stade") Stade stade, @RequestParam("nbr_places") int nbr_places, BindingResult result, @PathVariable("idPartie") String idPartie, Model model) {
        Partie partie = partieService.getPartieByIdPartie(idPartie);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

       // User currentUser = userRepository.getById(Long.parseLong(idUser));
        reservation.setPartie(partieService.getPartieByIdPartie(idPartie));
        UserDetails currentUser = (UserDetails) auth.getPrincipal();

        User user = userRepository.findByUsername(currentUser.getUsername());
       /* if (user == null) {
            // handle error
        }

        */

        //if nbr_place > of capacity of stade
        long remainingSeats =  partie.getStade().getCapacite() - nbr_places;

        if(remainingSeats < 0)
        {
            model.addAttribute("title","Seats not avaiable");
            model.addAttribute("remaining", partie.getStade().getCapacite());
            model.addAttribute("partie", partie);
                    return "reservation/nbr_placesRemaining";
        }
        reservation.setUser(user);

        reservation.setNbr_places(nbr_places);
        // Calcolate total price
        // i have to convert it
        BigDecimal nbr_places_bd = new BigDecimal(String.valueOf(nbr_places));
        //its different than the primitive type, to multiply a bigDecimal with and other BigDecimal i have to use
        //multiply
        BigDecimal prix_total = nbr_places_bd.multiply(partie.getPrix());

        reservation.setPrixTotal(prix_total);

        //Date purchased
        Date date_achat = new Date();
        reservation.setDateAchat(date_achat);

        reservationService.addReservation(reservation);
        model.addAttribute("partieId", idPartie);
        model.addAttribute("idUser", user.getIdUser());
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom());

        model.addAttribute("prix_total", prix_total);
        model.addAttribute("date_achat",date_achat);

        model.addAttribute("reservation", reservation);
        return "reservation/formPrixPayement";
    }
/*
    @GetMapping("/pay/{codeReservation}")
    public String selectPartiePlace(Model model, @PathVariable(name = "codeReservation") Long codeReservation, @ModelAttribute("reservation") Reservation reservation) {
        Reservation reservation = reservationService.getReservationById(codeReservation);

        model.addAttribute("partie", partie);
        model.addAttribute("user", user);


        model.addAttribute("reservation", new Reservation());
        model.addAttribute("title", "");
        return "reservation/indexReservPartie";
    }

 */
/*
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

 */



    /*@GetMapping("/selectPartie/{idPartie}")
    public String selectPartiePlace(Model model, @PathVariable(name = "idPartie") String idPartie, @ModelAttribute("user") User user) {
        Partie partie = partieService.getPartieByIdPartie(idPartie);

        model.addAttribute("partie", partie);
        model.addAttribute("user", user);


        model.addAttribute("reservation", new Reservation());
        model.addAttribute("title", "");
        return "reservation/indexReservPartie";
    }

     */
/*
    @GetMapping("/selectPartie/{idPartie}")
    public String selectPartiePlace(Model model, @PathVariable(name = "idPartie") String idPartie) {
        Partie partie = partieService.getPartieByIdPartie(idPartie);


        model.addAttribute("partie", partie);


        model.addAttribute("title", "");

        return "reservation/indexReservPartie";


    }

 */
    //@PathVariable quando mettiamo cio bisognera includere attributo nel Link
   /* @PostMapping("/reservationPartie/{idPartie}")
    public String selectPartiePlacetSubmit(@ModelAttribute("partie") Partie partie,@ModelAttribute("reservation") Reservation reservation, @ModelAttribute("user") User user, @ModelAttribute("idUser") String idUser, @RequestParam("nbr_places") int nbr_places,
                                     BindingResult result, @PathVariable("idPartie") String idPartie, Model model) {





        Long userId = Long.parseLong(idUser);
        User currentUser = userRepository.getById(userId);

        reservation.setPartie(partieService.getPartieByIdPartie(idPartie));
        reservation.setUser(currentUser);
        reservation.setNbr_places(nbr_places);



        reservationService.addReservation(reservation);

        model.addAttribute("partieId", idPartie);
        model.addAttribute("idUser",userId);
        model.addAttribute("reservation", reservation);

        return "redirect:/";




    }

    */

/*
    @GetMapping("/selectPartie/{codeReservation}")
    public String selectPartie(@ModelAttribute("partie") Partie partie, Model model)
    {
        List<Reservation> reservations = reservationService.getAllReservation();
        List<Partie> parties = partieService.getAllPartie();

        model.addAttribute("reservations",reservations);
        model.addAttribute("parties",parties);


        return "reservation/indexReservPartie";

    }

    @PostMapping("/reservationByPartie/{idPartie}")
    public String selectPlaces(@ModelAttribute("partie") Partie partie,@ModelAttribute("reservation") Reservation reservation,@PathVariable(name="idPartie") Long idPartie,@RequestParam int nbr_places, Model model)
    {

        this.nbr_places= nbr_places;

        model.addAttribute("partie",partie.getIdPartie());
        return "reservation/formPrixPayement";
    } //ok

    @GetMapping("/user")
    public String inserisciUser(Model model,@PathVariable(name="idUser")Long idUser,@ModelAttribute("reservation") Reservation reservation) {
        //Optional<User> user= userRepository.findById(idUser);
        List<Partie> parties= partieService.getAllPartie();

        model.addAttribute("user", user);
        model.addAttribute("parties", parties);
        double prixTotal = nbr_places * partie.getPrix();
        model.addAttribute("prixTotal",prixTotal);
        return "reservation/formPrixPayement";
    }


    @PostMapping("/reservation/user/payement")
    public String pagaPrenotazione(@ModelAttribute("user") User user,@ModelAttribute("partie") Partie partie)
    {
        Reservation reservation = new Reservation();
        reservation.setNbr_places(nbr_places);
        reservation.setDateAchat(LocalDateTime.now());
        reservation.setPaye(true);
        reservation.setUser(user);
        reservation.setPartie(partie);
        reservationService.addReservation(reservation);
        return "reservation/confirmed";
    }
    */


   /* @GetMapping("/reservationByPartieSummary/{idPartie}")
    public String summaryReservationByIdPartie(Model model, @PathVariable(name="idPartie") Long idPartie,@ModelAttribute("reservation") Reservation reservation)
    {
        Partie partie = partieService.getPartieById(idPartie);

        model.addAttribute("partie",partie);
        model.addAttribute("reservation",reservation);


        return "reservation/indexReservPartie";

    }

    */



    /*@PostMapping("/newReservation")
    public String  addSubmit(@Valid @ModelAttribute("reservation") Reservation reservation, BindingResult result, Model model){

        reservation.getCodeReservation();
        reservation.getDateAchat();
        reservation.getPaye();
        reservation.getPaye();
        reservation.getPrixTotal();
        reservation.getPartie();
        reservation.ge

    }

     */


        /*Reservation reservationEntity = convertDtoToEntity(reservationBody);
        if (reservationEntity == null) {
            return ResponseEntity.badRequest().body("Empty Request Body");
        }

        Optional<Reservation> reservations = Optional.ofNullable(reservationService.getReservationById(reservationEntity.getCodeReservation()));

        if (!reservations.isPresent()) {
            reservationService.addReservation(reservationEntity);
            return ResponseEntity.ok(convertEntityToDto(reservationEntity));
        } else {
            return ResponseEntity.badRequest().body("Reservation exists");
        }


    }
    */


    /*@DeleteMapping(path = "/{codeReservation}")
    public ResponseEntity deleteReservation(@PathVariable("codeReservation") Long codeReservation) {
        reservationService.deleteReservation(codeReservation);

        return ResponseEntity.ok(convertEntityToDto((codeReservation)));

    }

     */

   /* @PutMapping(path = "/")
    public ResponseEntity updateReservation(@PathVariable("id") Long codeReservation, @RequestBody ReservationDTO reservationBody) {
        if (reservationBody == null) {
            return ResponseEntity.badRequest().body("Empty Request Body");
        }

       // Optional<Reservation> reservations = Optional.ofNullable(reservationService.getReservationById(reservationBody.getCodeReservation()));

        if(!Objects.equals(codeReservation,reservationBody.getCodeReservation()))
        {
            throw new IllegalArgumentException("IDs don't match");
        }
        Reservation reservation = convertDtoToEntity(reservationBody);
        return  ResponseEntity.ok(reservationService.updateReservation(reservation));

    }

    */


}
