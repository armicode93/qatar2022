package com.example.qatar2022.entities.person;


import lombok.Data;

import javax.persistence.*;
import java.awt.*;
import java.util.Date;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idPerson;

    private String firstName;

    private String LastName;

    private int age;

    private Date dateBirth;

    @ManyToOne
    private Image image;
}
