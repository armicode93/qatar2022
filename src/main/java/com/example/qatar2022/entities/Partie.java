package com.example.qatar2022.entities;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    private int scoreEq1;

    private int scoreEq2;

    private Date date;

    private String tours;

}
