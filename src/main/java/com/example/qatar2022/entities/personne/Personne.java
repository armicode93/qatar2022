package com.example.qatar2022.entities.personne;


import com.example.qatar2022.entities.Image;
import lombok.*;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorColumn(name="personne_type",
        discriminatorType = DiscriminatorType.STRING)


@Inheritance(strategy = InheritanceType.SINGLE_TABLE)


public class Personne implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long cin;


    private String nom;


    private String prenom;



    private Date dateNaiss;




   @ManyToOne(fetch = FetchType.EAGER)
    private Image image;


}
