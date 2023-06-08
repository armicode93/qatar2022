package com.example.qatar2022.repository;


import com.example.qatar2022.entities.Reservation;
import com.example.qatar2022.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByReservation(Reservation reservation);
}
