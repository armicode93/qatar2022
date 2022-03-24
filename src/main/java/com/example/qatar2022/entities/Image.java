package com.example.qatar2022.entities;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data

public class Image {

    @Id
    @GeneratedValue
    private Long id;

    private String nom;
    private String type;

    @Column(name="image", length = 1000)
    private  byte[] imageByte;


}
