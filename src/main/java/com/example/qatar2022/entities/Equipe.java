package com.example.qatar2022.entities;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="equipe")
public class Equipe implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idEquipe;

    private String pays;

   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Image drapeau;


}
