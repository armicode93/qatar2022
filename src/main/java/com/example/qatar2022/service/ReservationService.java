package com.example.qatar2022.service;


import com.example.qatar2022.entities.Reservation;
import com.example.qatar2022.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.modelmapper.ModelMapper;


import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {


    @Autowired
    private  ReservationRepository reservationRepository;

    @Autowired
    private ModelMapper modelMapper;



   /* public List<ReservationDTO> getAllReservation()
    {
        return reservationRepository.findAll()
            .stream()
            .map(this::convertEntityToDto)
            .collect(Collectors.toList());
    }

    */


    /*

    private Reservation convertDtoToEntity(ReservationDTO reservationDTO)
    {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);

        Reservation reservation = new Reservation();
        reservation = modelMapper.map(reservationDTO, Reservation.class);
        return reservation;
    }

     */


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
    public String deleteReservation(Long idReservation)
    {
        boolean exists = reservationRepository.existsById(idReservation);
        if(!exists)
        {
            throw  new IllegalStateException("Not exists");
        }
        reservationRepository.deleteById(idReservation);
        return "ok delete";

    }

    public Reservation updateReservation(Reservation reservation)
    {
        reservationRepository.save(reservation);
        return reservation;
    }
}
