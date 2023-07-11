package com.example.qatar2022.entities;

import com.sun.istack.NotNull;
import java.io.Serializable;
import javax.persistence.*;
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

  @NotNull private String nomStade;

  @NotNull private Long capacite;

  public Stade(String nomStade) {
    this.nomStade = nomStade;
  }

  @Override
  public String toString() {
    return "Stade{" + "nomStade='" + nomStade + '\'' + '}';
  }
}
