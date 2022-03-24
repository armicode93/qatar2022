package com.example.qatar2022.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Match {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idMatch;

    @ManyToOne
    private Team team1;

    @ManyToOne
    private Team team2;

    @ManyToOne
    private Stadium stadium;

    private int scoreEq1;

    private int scoreEq2;

    private Date date;

    private String tours;

}
