package com.example.qatar2022.entities;


import com.example.qatar2022.entities.personne.Joueur;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="partie")
public class Partie implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idPartie;

    @ManyToOne


    private Equipe eq1;

    @ManyToOne

    private Equipe  eq2;

    @ManyToOne
    @JoinColumn(name="stade_id_stade")
    private Stade stade;

   /* @ManyToMany
    private List<Joueur> joueur = new ArrayList<>();

    */

    /*
    @OneToMany(mappedBy="partie")
    private List <Goal> goal = new ArrayList<>();

     */

    @ManyToOne
    @JoinColumn(name="tour_id_tour")
    private Tour tour ;

   /*@ManyToMany(mappedBy = "partie")
   private List <Joueur> joueur = new ArrayList<>();

    */

  /* @OneToMany(mappedBy = "partie")
   private List<Reservation> reservation = new ArrayList<>();

   */



    private int scoreEq1;

    private int scoreEq2;

    private LocalDateTime dateTime;



    private String arbitre_principal;

    private String totalTime;

    private String prolongation;

    private Double prix;



}
