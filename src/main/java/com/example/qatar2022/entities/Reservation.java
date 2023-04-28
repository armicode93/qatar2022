package com.example.qatar2022.entities;


import com.example.qatar2022.entities.personne.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table

@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codeReservation;

    private int nbr_places;

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

    public Long getCodeReservation() {
        return codeReservation;
    }

    public void setCodeReservation(Long codeReservation) {
        this.codeReservation = codeReservation;
    }

    public int getNbr_places() {
        return nbr_places;
    }

    public void setNbr_places(int nbr_places) {
        this.nbr_places = nbr_places;
    }

    public Double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public LocalDateTime getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDateTime dateAchat) {
        this.dateAchat = dateAchat;
    }

    public Boolean getPaye() {
        return paye;
    }

    public void setPaye(Boolean paye) {
        this.paye = paye;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }
}
