package com.example.qatar2022.entities.personne;

import com.example.qatar2022.entities.Equipe;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@Table(name = "joueur")
@AllArgsConstructor
@NoArgsConstructor
public class Joueur implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long idJoueur;

  @Size(
      min = 2,
      max = 60,
      message = "Le prénom doit comporter au moins 3 caractères et au maximum 60 caractères.")
  @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s'-]+$", message = "Uniquement les caractères alphabétiques")
  @NotEmpty(message = "Nom obligatoire et ne peut être vide")
  public String nom;

  @Size(
      min = 2,
      max = 60,
      message = "Le prénom doit comporter au moins 3 caractères et au maximum 60 caractères.")
  @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s'-]+$", message = "Uniquement les caractères alphabétiques")
  @NotEmpty(message = "Prenom obligatoire et ne peut être vide")
  public String prenom;

  public Boolean blessure;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "equipe_id_equipe")
  private Equipe equipe;


  public Joueur(String nom, String prenom, Boolean blessure) {
    this.nom = nom;
    this.prenom = prenom;
    this.blessure = blessure;
  }

  public Long getIdJoueur() {
    return idJoueur;
  }

  public void setIdJoueur(Long idJoueur) {
    this.idJoueur = idJoueur;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public Boolean getBlessure() {
    return blessure;
  }

  public void setBlessure(Boolean blessure) {
    this.blessure = blessure;
  }

  public Equipe getEquipe() {
    return equipe;
  }

  public void setEquipe(Equipe equipe) {
    this.equipe = equipe;
  }

  @Override
  public String toString() {
    return "nom '" + nom + '\'' + ", prenom '" + prenom + '\'';
  }
}
