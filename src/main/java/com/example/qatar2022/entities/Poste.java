package com.example.qatar2022.entities;

import com.example.qatar2022.entities.personne.Joueur;
import lombok.*;
import com.example.qatar2022.entities.Partie;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idPoste;



    @NotNull
    private String Enum;

    private LocalTime tempsEntree;

    private LocalTime tempsSortie;

  @ManyToOne

  @JoinColumn(name = "id_joueur")
    private Joueur joueur ;

   @ManyToOne

   @JoinColumn(name="id_partie")
    private Partie partie ;






}
