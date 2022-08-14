package com.example.qatar2022.controllers;


import com.example.qatar2022.dto.ReservationDTO;
import com.example.qatar2022.entities.Reservation;
import com.example.qatar2022.service.PartieService;
import com.example.qatar2022.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/Reservation")
@CrossOrigin(origins = "*")
public class ReservationController {


    private  final ReservationService reservationService;

    private final PartieService partieService;


    @Autowired
    private ModelMapper modelMapper;

    public ReservationController(ReservationService reservationService, PartieService partieService) {
        this.reservationService = reservationService;
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



    @GetMapping("/")
    public ResponseEntity findAll()
    {
        List<Reservation> reservations = reservationService.getAllReservation();
        return ResponseEntity.ok(reservations.stream().map(this::convertEntityToDto).collect(Collectors.toList()));
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


    @PostMapping("/")
    public ResponseEntity createReservation(@RequestBody ReservationDTO reservationBody) {
        Reservation reservationEntity = convertDtoToEntity(reservationBody);
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

    /*@DeleteMapping(path = "/{codeReservation}")
    public ResponseEntity deleteReservation(@PathVariable("codeReservation") Long codeReservation) {
        reservationService.deleteReservation(codeReservation);

        return ResponseEntity.ok(convertEntityToDto((codeReservation)));

    }

     */

    @PutMapping(path = "/")
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


}
