package com.example.qatar2022.entities.personne;

import com.example.qatar2022.entities.Equipe;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;

@Entity
@Data
@Table(name = "staff")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Staff implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long idStaff;

  @Size(
          min = 3,
          max = 60,
          message = "Le prénom should have at least 3 characters and max 60 characters")
  @Pattern(regexp = "[a-zA-Z\\s]+", message = "Uniquement les caractères alphabétiques")
  private String nom;

  @Size(
          min = 3,
          max = 60,
          message = "Le prénom should have at least 3 characters and max 60 characters")
  @Pattern(regexp = "[a-zA-Z\\s]+", message = "Uniquement les caractères alphabétiques")
  private String prenom;

  @ManyToOne
  @JoinColumn(name = "equipe_id_equipe")
  private Equipe equipe;

  @Pattern(
          regexp = "COACH|PREPARATEUR",
          message = "Les seules valeurs autorisées sont : COACH, PREPARATEUR")
  private String fonction;
}
