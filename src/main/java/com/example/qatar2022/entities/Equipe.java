package com.example.qatar2022.entities;


import com.example.qatar2022.entities.personne.Joueur;
import com.example.qatar2022.entities.personne.Staff;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="equipe")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Equipe implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)


    private Long idEquipe;


    private String pays;



    private Long nbr_points;



   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Image drapeau;

   /*
   @OneToMany(mappedBy="equipe", cascade = CascadeType.ALL)
    private List<Staff> staff = new ArrayList<>();

    */



   @ManyToOne
   @JoinColumn(name = "groupe_id_groupe")
   private Groupe groupe ;


   /*
   @OneToMany(mappedBy="equipe")
    private List <Joueur> joueur = new ArrayList<>();

    */




    /*
   @OneToMany

    private List <Partie> partie = new ArrayList<>();

     */







}
