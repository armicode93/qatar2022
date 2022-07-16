package com.example.qatar2022.entities;


import com.example.qatar2022.entities.person.Personne;
import com.example.qatar2022.entities.person.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name ="reservation")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codeReservation;

    private Double prixTotal;

    private Date dateAchat;

    private Time timeAchat;

    private Boolean paye;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Partie> partie = new ArrayList<>() ;

    @OneToMany
    private List <Ticket> ticket = new ArrayList<>();


}
