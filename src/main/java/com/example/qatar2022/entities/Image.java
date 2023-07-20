package com.example.qatar2022.entities;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "image")
public class Image implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nom;
  private String type;

  @Lob // to storage large objects into the database
  @Column(name = "image", length = 1000)
  private byte[] imageByte;



  public Image(String nom, byte[] imageByte) {
    this.nom = nom;
    this.imageByte = imageByte;
  }

  public Image(byte[] imageByte) {
    this.imageByte = imageByte;
  }

  public void setImageByte(byte[] imageByte) {
    this.imageByte = imageByte;
  }
}
