package com.example.qatar2022.entities;

import com.example.qatar2022.entities.personne.Joueur;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "equipe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Equipe implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long idEquipe;

  private String pays;

  private Long nbr_points;

   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Image drapeau;

  @OneToMany(mappedBy = "equipe")
  private List<Joueur> joueur = new ArrayList<>();

  public Equipe(String pays) {
    this.pays = pays;
  }

  public Equipe(String pays, Long nbr_points, Image drapeau) {
    this.pays = pays;
    this.nbr_points = nbr_points;
    this.drapeau = drapeau;
  }

  public Equipe(String pays, Long nbr_points) {
    this.pays = pays;
    this.nbr_points = nbr_points;
  }

  public String getPays() {
    return pays;
  }

  public void setPays(String pays) {
    this.pays = pays;
  }

  public Long getNbr_points() {
    return nbr_points;
  }

  public void setNbr_points(Long nbr_points) {
    this.nbr_points = nbr_points;
  }

  public Image getDrapeau() {
    return drapeau;
  }

  public void setDrapeau(Image drapeau) {
    this.drapeau = drapeau;
  }

  public List<Joueur> getJoueur() {
    return joueur;
  }

  public void setJoueur(List<Joueur> joueur) {
    this.joueur = joueur;
  }

  @Override
  public String toString() {
    return "Equipe{" +
            "pays='" + pays + '\'' +
            ", drapeau=" + drapeau +
            '}';
  }

}
