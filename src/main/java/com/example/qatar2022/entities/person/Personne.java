package com.example.qatar2022.entities.person;


import com.example.qatar2022.entities.Image;
import com.example.qatar2022.entities.Reservation;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="personne")

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Personne implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idPersonne;


    private String nom;


    private String prenom;



    private Date dateNaiss;


    private String username ;


    private String password;


    private String role;


    private String email;


    private Long gsm;

   @ManyToOne(fetch = FetchType.EAGER)
    private Image image;

   @OneToMany
   private List<Reservation> reservation = new ArrayList<>();
}
