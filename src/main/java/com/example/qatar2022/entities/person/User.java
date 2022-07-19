package com.example.qatar2022.entities.person;

import com.example.qatar2022.entities.Reservation;
import lombok.*;

import javax.persistence.*;
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

    @ElementCollection(fetch = FetchType.EAGER) //
    List <Role> role;


    private String email;


    private Long gsm;

    @OneToMany
    private List<Reservation> reservation = new ArrayList<>();




}
