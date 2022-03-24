package com.example.qatar2022.entities;


import lombok.Data;

import javax.persistence.*;
import java.awt.*;

@Data
@Entity
@Table(name="team")
public class Team {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long  idTeam ;
    private String country;

    @ManyToOne
    private Image flag;


}
