package com.example.qatar2022.entities;

import com.example.qatar2022.entities.person.Personne;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="spectateurs")
public class Spectateurs implements Serializable {

    @Id

    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long codeTicket;

    @ManyToOne
    private Personne spectateur ;

    @ManyToOne
    private Partie partie;

}
