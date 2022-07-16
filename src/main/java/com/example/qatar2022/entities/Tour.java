package com.example.qatar2022.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "tour")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTour;

    private String nomTour;

    @OneToMany
    private List<Partie> partie = new ArrayList<>();
}
