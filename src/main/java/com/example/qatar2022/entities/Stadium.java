package com.example.qatar2022.entities;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Stadium {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idStadium;

    private String nameStadium;

    private Long capacity;

    private String town;


}
