package com.example.qatar2022.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import lombok.*;

@Entity
@Data
@Table(name = "tour")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tour {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long idTour;

  @Pattern(
      regexp = "HUITIEME_DE_FINALE|QUARTS_DE_FINALE|DEMI_FINAL|FINAL",
      message = "nomTour only in this format: HUITIEME_DE_FINALE,QUARTS_DE_FINALE,DEMI_FINAL,FINAL")
  private String nomTour;

  @OneToMany(mappedBy = "tour")
  private List<Partie> partie = new ArrayList<>();

  public Tour(String nomTour) {
    this.nomTour = nomTour;
  }

  public List<Partie> getPartie() {

    return partie;
  }

  @Override
  public String toString() {
    return "Tour{" + "nomTour='" + nomTour + '\'' + ", partie=" + partie + '}';
  }
}
