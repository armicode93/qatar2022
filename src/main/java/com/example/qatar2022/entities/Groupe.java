package com.example.qatar2022.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="groupe")



public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idGroupe ;

    private String nomGroupe ;

    /*@OneToMany(mappedBy = "groupe")
    private List<Equipe> equipe = new ArrayList<>();

     */

    public Groupe() {
    }

    public Groupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }

    public Groupe(Long idGroupe) {
        this.idGroupe = idGroupe;
    }

    public Long getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(Long idGroupe) {
        this.idGroupe = idGroupe;
    }

    public String getNomGroupe() {
        return nomGroupe;
    }

    public void setNomGroupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }

    @Override
    public String toString() {
        return "Groupe{" +
                "nomGroupe='" + nomGroupe + '\'' +
                '}';
    }
}
