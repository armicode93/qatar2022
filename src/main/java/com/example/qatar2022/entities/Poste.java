package com.example.qatar2022.entities;

import com.example.qatar2022.entities.personne.Joueur;
import lombok.*;

import javax.persistence.*;


@Data
@Table(name="poste")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Poste {




    private String nomPoste;

    private Long tempsEntree;

    private Long tempsSortie;

  @ManyToOne
  @JoinColumn(name = "cin")
    private Joueur joueur ;

   @ManyToOne
   @JoinColumn(name="id_partie")
    private Partie partie ;




}
