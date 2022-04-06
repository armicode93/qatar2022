package com.example.qatar2022.entities.person;


import com.example.qatar2022.entities.Equipe;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name="Joueur")
public class Joueur extends Personne implements Serializable {

    @ManyToOne
    private Equipe equipe;

    private String  poste;

    private Boolean blessure;
}
