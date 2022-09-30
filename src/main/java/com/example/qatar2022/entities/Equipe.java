package com.example.qatar2022.entities;


import com.example.qatar2022.entities.personne.Joueur;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="equipe")
@Getter
@Setter



@AllArgsConstructor
@NoArgsConstructor

public class Equipe implements Serializable {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)


    private Long idEquipe;


    private String pays;



    private Long nbr_points;



    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Image drapeau;

   /*
   @OneToMany(mappedBy="equipe", cascade = CascadeType.ALL)
    private List<Staff> staff = new ArrayList<>();

    */



    @ManyToOne
    @JoinColumn(name = "groupe_id_groupe")
    private Groupe groupe ;



   @OneToMany(mappedBy="equipe")
   private List<Joueur> joueur = new ArrayList<>();






    /*
   @OneToMany

    private List <Partie> partie = new ArrayList<>();

     */


    public Equipe(String pays, Long nbr_points, Image drapeau, Groupe groupe) {
        this.pays = pays;
        this.nbr_points = nbr_points;
        this.drapeau = drapeau;
        this.groupe = groupe;
    }

    public Equipe(String pays) {
        this.pays = pays;
    }

    public Equipe(String pays, Long nbr_points, Image drapeau) {
        this.pays = pays;
        this.nbr_points = nbr_points;
        this.drapeau = drapeau;
    }



    @Override
    public String toString() {
        return "Equipe{" +
                "idEquipe=" + idEquipe +
                ", pays='" + pays + '\'' +
                ", nbr_points=" + nbr_points +
                ", drapeau=" + drapeau +
                ", groupe=" + groupe +
                '}';
    }
}

