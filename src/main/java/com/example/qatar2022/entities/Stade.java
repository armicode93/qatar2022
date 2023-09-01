package com.example.qatar2022.entities;

import com.sun.istack.NotNull;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.*;

@Entity
@Data
@Table(name = "stade")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Stade implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long idStade;

  /*

  @OneToMany(mappedBy="stade")

  private List<Partie> partie = new ArrayList<>();

   */
  @Size(min = 3, max = 60, message = "Le prénom doit comporter au moins 3 caractères et au maximum 60 caractères.")
  private String nomStade;

  @NotNull private Long capacite;

  public Stade(String nomStade) {
    this.nomStade = nomStade;
  }

  @Override
  public String toString() {
    return "Stade{" + "nomStade='" + nomStade + '\'' + '}';
  }
}
