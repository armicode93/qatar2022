package com.example.qatar2022.entities.person;

import com.example.qatar2022.entities.Reservation;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends Personne implements Serializable {

    private String username ;


    private String password;


    private String role;


    private String email;


    private Long gsm;

    @OneToMany
    private List<Reservation> reservation = new ArrayList<>();
}
