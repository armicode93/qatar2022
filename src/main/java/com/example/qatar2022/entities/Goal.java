package com.example.qatar2022.entities;

import com.example.qatar2022.entities.personne.Joueur;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import lombok.Data;


import java.time.LocalTime;

@Entity
@Data
@Table (name="goal")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Goal{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idGoal;

    @NotNull
    private LocalTime time;



    @ManyToOne
    @JoinColumn(name = "joueur_id")
    private Joueur joueur;


    @ManyToOne
    @JoinColumn(name="partie_id_partie")
    private Partie partie;

    @ManyToOne
    @JoinColumn(name="type_id_type")
    private Type type;
    




}
