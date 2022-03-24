package com.example.qatar2022.entities;

import com.example.qatar2022.entities.person.Person;
import com.example.qatar2022.entities.Match;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import javax.swing.text.View;

@Data
@Entity
@Table(name="spectator")
public class Spectator {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long codeTicket;

    @ManyToOne
    private Person spectator ;

    @ManyToOne
   private Match match;

}
