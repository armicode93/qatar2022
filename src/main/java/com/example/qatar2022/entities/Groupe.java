package com.example.qatar2022.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="groupe")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idGroupe ;

    private String nomGroupe ;

    /*@OneToMany(mappedBy = "groupe")
    private List<Equipe> equipe = new ArrayList<>();

     */
}
