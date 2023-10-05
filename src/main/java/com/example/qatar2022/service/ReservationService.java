package com.example.qatar2022.service;

import com.example.qatar2022.entities.Reservation;
import com.example.qatar2022.entities.personne.User;
import com.example.qatar2022.repository.ReservationRepository;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

  @Autowired private ReservationRepository reservationRepository;

  @Autowired private ModelMapper modelMapper;

  public List<Reservation> getAllReservation() {
    List<Reservation> reservations = new ArrayList<>();

    reservationRepository.findAll().forEach(reservations::add);

    return reservations;
  }

  public Reservation getReservationById(Long idReservation) {
    return reservationRepository.findById(idReservation).orElse(null);
  }

  public void addReservation(Reservation reservation) {
    reservationRepository.save(reservation);
  }

  public String deleteReservation(Long idReservation) {
    boolean exists = reservationRepository.existsById(idReservation);
    if (!exists) {
      throw new IllegalStateException("Not exists");
    }
    reservationRepository.deleteById(idReservation);
    return "ok delete";
  }

  public Reservation updateReservation(Reservation reservation) {
    reservationRepository.save(reservation);
    return reservation;
  }

  public List<Reservation> getReservationByUser(User user) {
    return reservationRepository.findAllByUser(user);
  }
}
