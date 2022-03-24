package com.example.qatar2022.entities.person;


import com.example.qatar2022.entities.Team;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Footballer extends Person{

    @ManyToOne
    private Team team;

    private Boolean injury;

    private String Role;
}
