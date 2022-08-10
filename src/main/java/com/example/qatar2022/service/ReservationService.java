package com.example.qatar2022.service;


import com.example.qatar2022.entities.Reservation;
import com.example.qatar2022.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List <Reservation> getAllReservation()
    {
        List<Reservation> reservations = new ArrayList<>();

        reservationRepository.findAll().forEach(reservations::add);

        return reservations;
    }

    public Reservation getReservationById(Long idReservation)
    {
        return reservationRepository.findById(idReservation).orElse(null);

    }

    public void addReservation(Reservation reservation)
    {
        reservationRepository.save(reservation);
    }
    public void deleteReservation(Long idReservation)
    {
        boolean exists = reservationRepository.existsById(idReservation);
        if(!exists)
        {
            throw  new IllegalStateException("Not exists");
        }
        reservationRepository.deleteById(idReservation);
    }

    public Reservation updateReservation(Long idReservation, Reservation reservation)
    {
        reservationRepository.save(reservation);
        return reservation;
    }
}
