package com.example.qatar2022.controllers;


import com.example.qatar2022.entities.Reservation;
import com.example.qatar2022.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/Reservation")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;


    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(reservationService.getAllReservation());
    }

    @GetMapping("/{codeReservation}")
    public ResponseEntity findReservationById(@PathVariable(name = "codeReservation") Long codeReservation) {
        if (codeReservation == null) {
            return ResponseEntity.badRequest().body("Empty parametre");
        }

        Optional<Reservation> reservation = Optional.ofNullable(reservationService.getReservationById(codeReservation));

        if (reservation.isPresent()) {
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity createReservation(@RequestBody Reservation reservationBody) {
        if (reservationBody == null) {
            return ResponseEntity.badRequest().body("Empty Request Body");
        }

        Optional<Reservation> reservations = Optional.ofNullable(reservationService.getReservationById(reservationBody.getCodeReservation()));

        if (!reservations.isPresent()) {
            reservationService.addReservation(reservationBody);
            return ResponseEntity.ok(reservationBody);
        } else {
            return ResponseEntity.badRequest().body("Reservation exists");
        }
    }

    @DeleteMapping(path = "{codeReservation}")
    public void deleteReservation(@PathVariable("codeReservation") Long codeReservation) {
        reservationService.deleteReservation(codeReservation);
    }

    @PutMapping(path = "/")
    public ResponseEntity updateReservation(@RequestBody Reservation reservationBody) {
        if (reservationBody == null) {
            return ResponseEntity.badRequest().body("Empty Request Body");
        }

        Optional<Reservation> reservations = Optional.ofNullable(reservationService.getReservationById(reservationBody.getCodeReservation()));

        if (reservations.isPresent()) {
            Reservation createReservation = reservationService.updateReservation(reservationBody.getCodeReservation(), reservationBody);

            return ResponseEntity.ok(reservationBody);
        }
        return ResponseEntity.notFound().build();
    }
}
