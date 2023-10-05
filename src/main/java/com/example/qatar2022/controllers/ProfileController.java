package com.example.qatar2022.controllers;

import com.example.qatar2022.entities.Reservation;
import com.example.qatar2022.entities.Ticket;
import com.example.qatar2022.entities.personne.User;
import com.example.qatar2022.repository.personne.UserRepository;
import com.example.qatar2022.service.ReservationService;
import com.example.qatar2022.service.TicketService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile/")
public class ProfileController {

  private final UserRepository userRepository;
  private final ReservationService reservationService;
  private final TicketService ticketService;

  public ProfileController(
      UserRepository userRepository,
      ReservationService reservationService,
      TicketService ticketService) {
    this.userRepository = userRepository;
    this.reservationService = reservationService;
    this.ticketService = ticketService;
  }

  @GetMapping("index")
  public String index(Model model) {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();

    User user = userRepository.findByUsername(username);

    List<Reservation> userReservations = reservationService.getReservationByUser(user);



    List<Ticket> userTickets = new ArrayList<>();

    for (Reservation reservation : userReservations) {
      List<Ticket> reservationTickets = ticketService.getTicketsByReservation(reservation);
      userTickets.addAll(reservationTickets);
    }

    model.addAttribute("user", user);
    model.addAttribute("userTickets", userTickets);
    model.addAttribute("userReservations", userReservations);

    return "profile/index";
  }
}
