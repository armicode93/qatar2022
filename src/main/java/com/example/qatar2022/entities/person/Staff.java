package com.example.qatar2022.entities.person;

import com.example.qatar2022.entities.Equipe;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Staff extends Personne implements Serializable  {

    @ManyToOne
    private Equipe equipe;

    private String fonction;

}
