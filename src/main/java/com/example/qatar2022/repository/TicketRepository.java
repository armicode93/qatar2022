package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Reservation;
import com.example.qatar2022.entities.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

  List<Ticket> findByReservation(Reservation reservation);
}
