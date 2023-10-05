package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.Reservation;
import com.example.qatar2022.entities.personne.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  List<Reservation> findByPartie(Partie partie);

  List<Reservation> findAllByUser(User user);
}
