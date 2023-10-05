package com.example.qatar2022.entities;

import com.example.qatar2022.entities.personne.Joueur;
import java.io.Serializable;
import java.time.LocalTime;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import lombok.*;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Poste implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long idPoste;

  @ManyToOne
  @JoinColumn(name = "joueur_id_joueur")
  private Joueur joueur;

  @ManyToOne
  @JoinColumn(name = "partie_id_partie")
  private Partie partie;

  @Pattern(
      regexp = "GARDIEN|DEFENSEUR|MILIEU|GARDIEN|ATTAQUANT|BANC",
      message = "Les seules valeurs autorisées sont : DEFENSEUR, MILIEU, GARDIEN, ATTAQUANT, BANC")
  public String nomPoste;

  private LocalTime tempsEntree;

  private LocalTime tempsSortie;

  @Override
  public String toString() {
    return "Poste{"
        + ", nomPoste='"
        + nomPoste
        + '\''
        + ", tempsEntree="
        + tempsEntree
        + ", tempsSortie="
        + tempsSortie
        + '}';
  }
}
