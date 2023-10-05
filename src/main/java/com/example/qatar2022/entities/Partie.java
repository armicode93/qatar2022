package com.example.qatar2022.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;
import org.springframework.lang.Nullable;

@Entity
@Data
@Table(name = "partie")
public class Partie implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long idPartie;

  @ManyToOne(cascade = CascadeType.PERSIST)
  private Equipe eq1;

  @ManyToOne(cascade = CascadeType.PERSIST)
  private Equipe eq2;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "stade_id_stade")
  private Stade stade;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "tour_id_tour")
  private Tour tour;

  @Nullable private Integer scoreEq1;

  @Nullable private Integer scoreEq2;

  @FutureOrPresent(message = "DateTime ne peut être inférieure à la date du jour")
  private LocalDateTime dateTime;

  @Size(min = 2, max = 20, message = " Mettez un nom valide ")
  @Pattern(
      regexp = "^[a-zA-Z]+$",
      message = "Mettez un nom valide contenant uniquement des lettres")
  private String arbitre_principal;

  @Pattern(
      regexp = "^\\d{1,3}:\\d{2}$",
      message = "La durée totale doit être indiquée au format HH:MM")
  private String totalTime;

  @Size(min = 2, max = 3, message = "La prolongation ne peut avoir que deux valeurs : YES ou NO")
  private String prolongation;

  private BigDecimal prix;

  public Partie(
      Equipe eq1,
      Equipe eq2,
      Stade stade,
      Tour tour,
      int scoreEq1,
      int scoreEq2,
      LocalDateTime dateTime,
      String arbitre_principal,
      String totalTime,
      String prolongation,
      BigDecimal prix) {
    this.eq1 = eq1;
    this.eq2 = eq2;
    this.stade = stade;
    this.tour = tour;
    this.scoreEq1 = scoreEq1;
    this.scoreEq2 = scoreEq2;
    this.dateTime = dateTime;
    this.arbitre_principal = arbitre_principal;
    this.totalTime = totalTime;
    this.prolongation = prolongation;
    this.prix = prix;
  }

  public Partie(Long idPartie, Integer scoreEq1, Integer scoreEq2) {
    this.idPartie = idPartie;
    this.scoreEq1 = scoreEq1;
    this.scoreEq2 = scoreEq2;
  }

  public Partie(
      Long idPartie,
      Stade stade,
      LocalDateTime dateTime,
      String arbitre_principal,
      BigDecimal prix) {
    this.idPartie = idPartie;
    this.stade = stade;
    this.dateTime = dateTime;
    this.arbitre_principal = arbitre_principal;
    this.prix = prix;
  }

  public Partie() {}

  public Partie(
      Equipe eq1,
      Equipe eq2,
      Stade stade,
      Tour tour,
      LocalDateTime dateTime,
      String arbitre_principal,
      BigDecimal prix) {
    this.eq1 = eq1;
    this.eq2 = eq2;
    this.stade = stade;
    this.tour = tour;

    this.dateTime = dateTime;
    this.arbitre_principal = arbitre_principal;
    this.prix = prix;
  }

  public Partie(Equipe eq1, Equipe eq2) {
    this.eq1 = eq1;
    this.eq2 = eq2;
  }

  public Integer getScoreEq1() {
    return scoreEq1;
  }

  public void setScoreEq1(Integer scoreEq1) {
    this.scoreEq1 = scoreEq1;
  }

  public Integer getScoreEq2() {
    return scoreEq2;
  }

  public void setScoreEq2(Integer scoreEq2) {
    this.scoreEq2 = scoreEq2;
  }

  public Long getIdPartie() {
    return idPartie;
  }

  public void setIdPartie(Long idPartie) {
    this.idPartie = idPartie;
  }

  public Equipe getEq1() {
    return eq1;
  }

  public void setEq1(Equipe eq1) {
    this.eq1 = eq1;
  }

  public Equipe getEq2() {
    return eq2;
  }

  public void setEq2(Equipe eq2) {
    this.eq2 = eq2;
  }

  public BigDecimal getPrix() {
    return prix;
  }

  public void setPrix(BigDecimal prix) {
    this.prix = prix;
  }

  public Stade getStade() {
    return stade;
  }

  public void setStade(Stade stade) {
    this.stade = stade;
  }

  public Tour getTour() {
    return tour;
  }

  public void setTour(Tour tour) {
    this.tour = tour;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public String getDateTimeAsString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    LocalDateTime dateTime = getDateTime();
    return dateTime.format(formatter);
  }

  public String getArbitre_principal() {
    return arbitre_principal;
  }

  public void setArbitre_principal(String arbitre_principal) {
    this.arbitre_principal = arbitre_principal;
  }

  public String getTotalTime() {
    return totalTime;
  }

  public void setTotalTime(String totalTime) {
    this.totalTime = totalTime;
  }

  public String getProlongation() {
    return prolongation;
  }

  public void setProlongation(String prolongation) {
    this.prolongation = prolongation;
  }

  @Transient
  public String getPhotosImagePathEquipe1() {
    if (eq1 == null || eq1.getDrapeau() == null || eq1.getIdEquipe() == null) return null;

    return "/images/equipe/" + eq1.getIdEquipe() + "/" + eq1.getDrapeau();
  }

  @Transient
  public String getPhotosImagePathEquipe2() {
    if (eq2 == null || eq2.getDrapeau() == null || eq2.getIdEquipe() == null) return null;

    return "/images/equipe/" + eq2.getIdEquipe() + "/" + eq2.getDrapeau();
  }

  @Override
  public String toString() {
    return "Partie{"
        + "eq1="
        + eq1
        + ", eq2="
        + eq2
        + ", stade="
        + stade
        + ", scoreEq1="
        + scoreEq1
        + ", scoreEq2="
        + scoreEq2
        + ", dateTime="
        + dateTime
        + ", arbitre_principal='"
        + arbitre_principal
        + '\''
        + ", totalTime='"
        + totalTime
        + '\''
        + ", prolongation='"
        + prolongation
        + '\''
        + ", prix="
        + prix
        + '}';
  }
}
