package com.example.qatar2022.controllers;

import com.example.qatar2022.dto.ReservationDTO;
import com.example.qatar2022.entities.*;
import com.example.qatar2022.entities.personne.User;
import com.example.qatar2022.repository.personne.UserRepository;
import com.example.qatar2022.service.PartieService;
import com.example.qatar2022.service.ReservationService;
import com.example.qatar2022.service.StadeService;
import com.example.qatar2022.service.TicketService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
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

@Controller
@CrossOrigin(origins = "*")
public class ReservationController {

  @Autowired private final ReservationService reservationService;

  @Autowired private final StadeService stadeService;

  @Autowired private final TicketService ticketService;

  @Autowired private final PartieService partieService;
  @Autowired private final UserRepository userRepository;
  private Partie partie;
  private int nbr_places;

  @Autowired private ModelMapper modelMapper;

  public ReservationController(
      ReservationService reservationService,
      StadeService stadeService,
      TicketService ticketService,
      PartieService partieService,
      UserRepository userRepository) {
    this.reservationService = reservationService;
    this.stadeService = stadeService;
    this.ticketService = ticketService;
    this.partieService = partieService;
    this.userRepository = userRepository;
  }

  private ReservationDTO convertEntityToDto(Reservation reservation) {
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

    ReservationDTO reservationDTO = new ReservationDTO();
    reservationDTO = modelMapper.map(reservation, ReservationDTO.class);
    return reservationDTO;
  }

  private Reservation convertDtoToEntity(ReservationDTO reservationDTO) {
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

    Reservation reservation = new Reservation();
    reservation = modelMapper.map(reservationDTO, Reservation.class);
    return reservation;
  }

  @GetMapping("/reservation/{codeReservation}")
  public ResponseEntity findReservationById(
      @PathVariable(name = "codeReservation") Long codeReservation) {

    if (codeReservation == null) {
      return ResponseEntity.badRequest().body("Empty parametre");
    }

    Optional<Reservation> reservation =
        Optional.ofNullable(reservationService.getReservationById(codeReservation));

    if (reservation.isPresent()) {

      return ResponseEntity.ok(
          convertEntityToDto(reservationService.getReservationById(codeReservation)));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/selectPlace/{idPartie}")
  public String selectPartiePlace(
      Model model,
      @PathVariable(name = "idPartie") String idPartie,
      @ModelAttribute("user") User user,
      @ModelAttribute("stade") Stade stade) {
    Partie partie = partieService.getPartieByIdPartie(idPartie);

    // I have to check if match has already been played
    LocalDateTime currentDateTime = LocalDateTime.now();
    if (partie.getDateTime().isBefore(currentDateTime)) {
      model.addAttribute("title", "Match already played");
      return "reservation/matchPlayed";
    }
    if (partie.getPrix() == null || partie.getStade() == null) {
      model.addAttribute("title", "Match Not Bookable yet");
      return "reservation/matchNoReservable";
    }

    if (partie.getStade().getCapacite() == 0) {
      model.addAttribute("title", "Match sold out");
      return "reservation/MatchSoldOut";
    }
    model.addAttribute("partie", partie);
    model.addAttribute("user", user);

    model.addAttribute("reservation", new Reservation());
    model.addAttribute("title", "");
    return "reservation/indexReservPartie";
  }

  @PostMapping("/reservationPartie/{idPartie}")
  public String selectPartiePlacetSubmit(
      @ModelAttribute("reservation") Reservation reservation,
      @ModelAttribute("stade") Stade stade,
      @RequestParam("nbr_places") int nbr_places,
      BindingResult result,
      @PathVariable("idPartie") String idPartie,
      Model model) {
    Partie partie = partieService.getPartieByIdPartie(idPartie);

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    // User currentUser = userRepository.getById(Long.parseLong(idUser));
    reservation.setPartie(partieService.getPartieByIdPartie(idPartie));
    UserDetails currentUser = (UserDetails) auth.getPrincipal();
    User user = userRepository.findByUsername(currentUser.getUsername());

    long remainingSeats = partie.getStade().getCapacite() - nbr_places;

    if (remainingSeats < 0) {
      model.addAttribute("title", "Seats not avaiable");
      model.addAttribute("remaining", partie.getStade().getCapacite());
      model.addAttribute("partie", partie);
      return "reservation/nbr_placesRemaining";
    }
    reservation.setUser(user);

    reservation.setNbr_places(nbr_places);
    // Calcolate total price
    // i have to convert it
    BigDecimal nbr_places_bd = new BigDecimal(String.valueOf(nbr_places));
    // its different than the primitive type, to multiply a bigDecimal with and other BigDecimal i
    // have to use
    // multiply
    BigDecimal prix_total = nbr_places_bd.multiply(partie.getPrix());

    reservation.setPrixTotal(prix_total);

    // Date purchased
    Date date_achat = new Date();
    reservation.setDateAchat(date_achat);

    reservationService.addReservation(reservation);
    model.addAttribute("partieId", idPartie);
    model.addAttribute("idUser", user.getIdUser());
    model.addAttribute("nom", user.getNom());
    model.addAttribute("prenom", user.getPrenom());

    model.addAttribute("prix_total", prix_total);
    model.addAttribute("date_achat", date_achat);

    model.addAttribute("reservation", reservation);
    return "reservation/formPrixPayement";
  }
}
