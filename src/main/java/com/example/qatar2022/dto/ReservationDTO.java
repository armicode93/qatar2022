package com.example.qatar2022.dto;

import com.example.qatar2022.entities.personne.User;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationDTO {
    private Long codeReservation;

    private Double prixTotal;

    private Date dateAchat;



    private Boolean paye;

    private User user;

   private Long idpartie ;
}
