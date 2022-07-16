package com.example.qatar2022.entities;


import com.example.qatar2022.entities.person.Joueur;
import com.example.qatar2022.entities.person.Staff;
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

   @OneToMany
    private List<Staff> staff = new ArrayList<>();

   @ManyToOne
    private Groupe groupe ;

   @OneToMany
    private List <Joueur> joueur = new ArrayList<>();

   @ManyToMany
    private List <Partie> partie = new ArrayList<>();





}
