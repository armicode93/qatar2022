package com.example.qatar2022.entities;




import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data

@Table(name="image")
public class Image implements Serializable {

    @Id
    @GeneratedValue
    private Long id;




    private String nom;
    private String type;

    @Column(name="image", length = 1000)
    private  byte[] imageByte;


}
