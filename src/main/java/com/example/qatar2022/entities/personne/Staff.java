package com.example.qatar2022.entities.personne;

import com.example.qatar2022.entities.Equipe;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name="staff")

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


public class Staff implements Serializable {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idStaff;

    @ManyToOne
    @JoinColumn(name="equipe_id_equipe")
    private Equipe equipe;

    private String fonction;

}

