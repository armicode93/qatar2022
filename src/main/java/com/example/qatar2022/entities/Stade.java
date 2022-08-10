package com.example.qatar2022.entities;


import com.sun.istack.NotNull;
import lombok.*;
import com.example.qatar2022.entities.Partie;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="stade")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Stade implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idStade;


    @OneToMany(mappedBy="stade")

    private List<Partie> partie = new ArrayList<>();



    @NotNull
    private String nomStade;

    @NotNull
    private Long capacite;





}
