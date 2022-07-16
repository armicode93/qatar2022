package com.example.qatar2022.entities;


import com.example.qatar2022.entities.person.Joueur;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
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
    private Stade stade;

    @ManyToMany
    private List<Joueur> joueur = new ArrayList<>();

    @OneToMany
    private List <Goal> goal = new ArrayList<>();

    /*@ManyToOne
    private Tour tours ;

     */



    private int scoreEq1;

    private int scoreEq2;

    private Date date;

    private Time time;

    private String arbitre_principal;

    private Time totalTime;

    private String prolongation;

    private Double prix;

    private String tours;

}
