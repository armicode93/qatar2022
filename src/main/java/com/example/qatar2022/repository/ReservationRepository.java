package com.example.qatar2022.repository;


import com.example.qatar2022.entities.Reservation;
import com.example.qatar2022.entities.person.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    Reservation findByCodeReservation(Long codeReservation);
    List<User> findByUser(User user);
    Reservation findByDateAchat(Date dateAchat);

}
