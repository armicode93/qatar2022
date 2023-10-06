package com.example.qatar2022.entities;

import com.example.qatar2022.entities.personne.Joueur;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.example.qatar2022.entities.personne.Staff;
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

  @Column(nullable = true, length = 64)
  private String drapeau;

  @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Joueur> joueur = new ArrayList<>();

  //to remove en cascade tut le chose associee
  @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Staff> staff = new ArrayList<>();

  public Equipe(String pays) {
    this.pays = pays;
  }

  public Equipe(String pays, String drapeau) {
    this.pays = pays;
    this.drapeau = drapeau;
  }

  public List<Staff> getStaff() {
    return staff;
  }

  public void setStaff(List<Staff> staff) {
    this.staff = staff;
  }

  public String getPays() {
    return pays;
  }

  public void setPays(String pays) {
    this.pays = pays;
  }

  public String getDrapeau() {
    return drapeau;
  }

  public void setDrapeau(String drapeau) {
    this.drapeau = drapeau;
  }

  public List<Joueur> getJoueur() {
    return joueur;
  }

  public void setJoueur(List<Joueur> joueur) {
    this.joueur = joueur;
  }

  @Transient
  public String getPhotosImagePath() {
    if (drapeau == null || idEquipe == null) return null;

    return "/images/equipe/" + idEquipe + "/" + drapeau;
  }

  @Override
  public String toString() {
    return "Equipe{" + "pays='" + pays + '\'' + ", drapeau=" + drapeau + '}';
  }
}
