package com.example.qatar2022.controllers;

import com.example.qatar2022.entities.Ticket;
import com.example.qatar2022.repository.personne.UserRepository;
import com.example.qatar2022.service.TicketService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
@CrossOrigin(origins = "*")
public class TicketController {

  private final TicketService ticketService;
  private final UserRepository userRepository;

  public TicketController(TicketService ticketService, UserRepository userRepository) {
    this.ticketService = ticketService;
    this.userRepository = userRepository;
  }

  @GetMapping("/")
  public ResponseEntity findAll() {
    return ResponseEntity.ok(ticketService.getAllTicket());
  }

  @GetMapping("/{codeTicket}")
  public ResponseEntity findTicketById(@PathVariable(name = "codeTicket") Long codeTicket) {
    if (codeTicket == null) {
      return ResponseEntity.badRequest().body("Empty parametre");
    }

    Optional<Ticket> tickets = Optional.ofNullable(ticketService.getTicketById(codeTicket));

    if (tickets.isPresent()) {
      return ResponseEntity.ok(tickets);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/")
  public ResponseEntity createTicket(@RequestBody Ticket ticketBody) {
    if (ticketBody == null) {
      return ResponseEntity.badRequest().body("Empty Request Body");
    }

    Optional<Ticket> tickets =
        Optional.ofNullable(ticketService.getTicketById(ticketBody.getCodeTicket()));

    if (!tickets.isPresent()) {
      ticketService.addTicket(ticketBody);
      return ResponseEntity.ok(ticketBody);
    } else {
      return ResponseEntity.badRequest().body("Ticket exist");
    }
  }

  @DeleteMapping(path = "{codeTicket}")
  public void deleteTicket(@PathVariable("codeTicket") Long codeTicket) {
    ticketService.deleteTicket(codeTicket);
  }

  @PutMapping(path = "/")
  public ResponseEntity updateTicket(@RequestBody Ticket ticketBody) {
    if (ticketBody == null) {
      return ResponseEntity.badRequest().body("Empty Request Body");
    }

    Optional<Ticket> ticket =
        Optional.ofNullable(ticketService.getTicketById(ticketBody.getCodeTicket()));

    if (ticket.isPresent()) {
      Ticket createTicket = ticketService.updateTicket(ticketBody.getCodeTicket(), ticketBody);
      return ResponseEntity.ok(ticketBody);
    }
    return ResponseEntity.notFound().build();
  }
}
