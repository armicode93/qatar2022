package com.example.qatar2022.entities;

import com.example.qatar2022.entities.personne.Joueur;
import com.sun.istack.NotNull;
import java.time.LocalTime;
import javax.persistence.*;
import lombok.*;
import lombok.Data;

@Entity
@Data
@Table(name = "goal")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Goal {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long idGoal;

  @NotNull private LocalTime time;

  @ManyToOne
  @JoinColumn(name = "joueur_id")
  private Joueur joueur;

  @ManyToOne
  @JoinColumn(name = "partie_id_partie")
  private Partie partie;
}
