package com.example.qatar2022.entities;


import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name="stade")
public class Stade implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idStade;

    @NotNull
    private String nomStade;

    @NotNull
    private Long capacite;

    @NotNull
    private String town;


}
