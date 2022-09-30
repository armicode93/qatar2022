package com.example.qatar2022.entities;

import com.example.qatar2022.entities.personne.Joueur;
import lombok.*;
import com.example.qatar2022.entities.Partie;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;


@Data
@Entity
@Table(name="poste")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Poste implements Serializable {

    @EmbeddedId
    private PostePk id;


    private String nomPoste;

    private LocalTime tempsEntree;

    private LocalTime tempsSortie;

  @ManyToOne
  @MapsId("joueur_id")
  @JoinColumn(name = "id_joueur")
    private Joueur joueur ;

   @ManyToOne
   @MapsId("partie_id")
   @JoinColumn(name="id_partie")
    private Partie partie ;






}
