package com.example.qatar2022.service;

import com.example.qatar2022.entities.Reservation;
import com.example.qatar2022.entities.Ticket;
import com.example.qatar2022.repository.TicketRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TicketService {

  private final TicketRepository ticketRepository;

  public TicketService(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  public List<Ticket> getAllTicket() {
    List<Ticket> tickets = new ArrayList<>();

    ticketRepository.findAll().forEach(tickets::add);
    return tickets;
  }

  public Ticket getTicketById(Long codeTicket) {
    return ticketRepository.findById(codeTicket).orElse(null);
  }

  public void addTicket(Ticket ticket) {
    ticketRepository.save(ticket);
  }

  public List<Ticket> getTicketsByReservation(Reservation reservation) {

    return ticketRepository.findByReservation(reservation);
  }

  public void deleteTicket(Long codeTicket) {
    boolean exists = ticketRepository.existsById(codeTicket);
    if (!exists) {
      throw new IllegalStateException("Not exists");
    }
    ticketRepository.deleteById(codeTicket);
  }

  public Ticket updateTicket(Long codeTicket, Ticket ticket) {
    ticketRepository.save(ticket);

    return ticket;
  }
}
