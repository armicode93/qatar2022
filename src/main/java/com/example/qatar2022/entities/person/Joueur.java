package com.example.qatar2022.entities.person;


import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Goal;
import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.Poste;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


public class Joueur extends Personne implements Serializable {

    @ManyToOne
    private Equipe equipe;
    

    @ManyToMany
    @JoinColumn(name = "idPartie")
    private List<Partie> partie;

    @OneToMany
    private List <Goal> goal = new ArrayList<>();
    @ManyToMany
    private List <Poste> poste = new ArrayList<>();

    private Boolean blessure;
}
