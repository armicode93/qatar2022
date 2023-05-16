package com.example.qatar2022.repository;


import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByPartie(Partie partie);


}
