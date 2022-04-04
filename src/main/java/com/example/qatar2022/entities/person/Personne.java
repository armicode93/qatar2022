package com.example.qatar2022.entities.person;


import com.example.qatar2022.entities.Image;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name="personne")
public class Personne implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long cin;

    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @NotNull
    private int age;
    @NotNull
    private Date dateNaiss;

   @ManyToOne(fetch = FetchType.EAGER)
    private Image image;
}
