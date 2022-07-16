package com.example.qatar2022.entities;

import com.example.qatar2022.entities.person.Joueur;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

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
    private Time time;



    @ManyToOne
    @JoinColumn(name = "joueur_cin")
    private Joueur joueur;


    @ManyToOne
    private Partie partie;

    @ManyToOne
    private Type type;
    




}
