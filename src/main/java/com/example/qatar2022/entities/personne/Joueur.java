package com.example.qatar2022.entities.personne;


import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Goal;
import com.example.qatar2022.entities.Partie;
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
@DiscriminatorValue("joueur")



public class Joueur extends Personne implements Serializable {

    @ManyToOne
    @JoinColumn(name="equipe_id_equipe")
    private Equipe equipe;
    

   /* @ManyToone
    @JoinColumn(name = "idPartie")
    private List<Partie> partie;

    */


  /*  @OneToMany(mappedBy = "joueur")
    private List <Goal> goal = new ArrayList<>();

   */


   /* @ManyToMany
    @JoinTable(name = "poste")
    private List <Partie> partie = new ArrayList<>();

    */




    private Boolean blessure;
}
