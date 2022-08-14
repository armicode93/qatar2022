package com.example.qatar2022.entities;


import com.example.qatar2022.entities.personne.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
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



    private Boolean paye;

    @ManyToOne
    @JoinColumn(name="user_cin")
    private User user;

    @ManyToOne
    @JoinColumn(name="partie_id_partie")
    private Partie partie ;

    /*
    @OneToMany(mappedBy = "reservation")
    private List <Ticket> ticket = new ArrayList<>();

     */


}
