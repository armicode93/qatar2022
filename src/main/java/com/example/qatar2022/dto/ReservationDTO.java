package com.example.qatar2022.dto;

import com.example.qatar2022.entities.personne.User;
import java.util.Date;
import lombok.Data;

@Data
public class ReservationDTO {
  private Long codeReservation;

  private Double prixTotal;

  private Date dateAchat;

  private Boolean paye;

  private int nbr_places;

  private User user;

  private Long idpartie;
}
