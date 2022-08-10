package com.example.qatar2022.entities.personne;

import com.example.qatar2022.entities.Equipe;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("staff")

public class Staff extends Personne implements Serializable  {

    @ManyToOne
    @JoinColumn(name="equipe_id_equipe")
    private Equipe equipe;

    private String fonction;

}
