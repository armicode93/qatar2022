package com.example.qatar2022.entities;

import com.example.qatar2022.entities.person.Joueur;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
//RIGUARDARE QUA HO TOLTO ANNOTATION ENTITY , NO ID QUA ONLY fk
@Entity
@Data
@Table(name="poste")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Poste {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPoste;

    private String nomPoste;

    private Long tempsEntree;

    private Long tempsSortie;

    @ManyToMany
    private List<Joueur> joueur = new ArrayList<>();

    @ManyToMany
    private List <Partie> partie = new ArrayList<>();


}
