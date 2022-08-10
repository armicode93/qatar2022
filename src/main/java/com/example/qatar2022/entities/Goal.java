package com.example.qatar2022.entities;

import com.example.qatar2022.entities.personne.Joueur;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import lombok.Data;

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
    private Long time;



    @ManyToOne
    @JoinColumn(name = "joueur_cin")
    private Joueur joueur;


    @ManyToOne
    @JoinColumn(name="partie_id_partie")
    private Partie partie;

    @ManyToOne
    @JoinColumn(name="type_id_type")
    private Type type;
    




}
