package com.example.qatar2022.entities;


import com.example.qatar2022.entities.personne.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codeReservation;

    private Double prixTotal;

    private LocalDateTime dateAchat;



    private Boolean paye;

    @ManyToOne
    @JoinColumn(name="user_id_user")
    private User user;



    @ManyToOne
    @JoinColumn(name="partie_id_partie")
    private Partie partie ;

    /*
    @OneToMany(mappedBy = "reservation")
    private List <Ticket> ticket = new ArrayList<>();

     */


}
