package com.example.qatar2022.controllers;


import com.example.qatar2022.dto.ReservationDTO;
import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.Reservation;
import com.example.qatar2022.service.PartieService;
import com.example.qatar2022.service.ReservationService;
import com.example.qatar2022.service.TicketService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;


@Controller

// Rest controller invece ritorna il oggetto vero e proprio o testo
@RequestMapping("/reservation")
@CrossOrigin(origins = "*")
public class ReservationController {


    @Autowired
    private  final ReservationService reservationService;

    @Autowired
    private final TicketService ticketService;

    @Autowired
    private final PartieService partieService;


    @Autowired
    private ModelMapper modelMapper;

    public ReservationController(ReservationService reservationService, TicketService ticketService, PartieService partieService) {
        this.reservationService = reservationService;
        this.ticketService = ticketService;
        this.partieService = partieService;
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

   /* @GetMapping("/reservationByPartie/{idPartie}")
    public String indexReservationByIdPartie(Model model, @PathVariable(name="idPartie") Long idPartie)
    {
        Partie partie = partieService.getPartieById(idPartie);

        model.addAttribute("partie",partie);

        return "reservation/indexReservEquipe";

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
