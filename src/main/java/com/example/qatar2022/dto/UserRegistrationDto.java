package com.example.qatar2022.dto;

import com.example.qatar2022.config.annotation.UniqueEmail;
import com.example.qatar2022.config.annotation.UniqueUsername;
import com.example.qatar2022.entities.personne.Role;
import java.time.LocalDate;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

public class UserRegistrationDto {

  @Size(
      min = 3,
      max = 60,
      message = "Le nom doit comporter au moins 3 caractères et au maximum 60 caractères.")
  @Pattern(regexp = "[a-zA-Z\\s]+", message = "Uniquement les caractères alphabétiques")

  private String nom;

  @Size(
      min = 3,
      max = 60,
      message = "Le prénom should have at least 3 characters and max 60 characters")
  @Pattern(regexp = "[a-zA-Z\\s]+", message = "Uniquement les caractères alphabétiques")

  private String prenom;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateNaiss;

  @Size(
      min = 4,
      max = 60,
      message = "Le nom d'utilisateur doit comporter au moins 3 caractères et au maximum 60 caractères")

  @UniqueUsername(message = "Le nom d'utilisateur existe déjà")
  private String username;

  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
      message =
          "Le mot de passe doit contenir au moins une lettre minuscule, une lettre majuscule et un chiffre.")
  @Size(
      min = 4,
      max = 15,
      message = "Le mot de passe doit comporter au moins 4 caractères et au maximum 15 caractères.")
  private String password;

  @Email(message = "Insérer un e-mail ")
  @NotNull(message = "Email obligatoire et ne peut être vide")
  @UniqueEmail(message = "Email existe déjà")
  private String email;

  @NumberFormat
  @NotNull(message = "value is required and can t be empty")
  private Long gsm;

  private Role role;

  public UserRegistrationDto(
      String nom,
      String prenom,
      LocalDate dateNaiss,
      String username,
      String password,
      String email,
      Long gsm,
      Role role) {
    this.nom = nom;
    this.prenom = prenom;
    this.dateNaiss = dateNaiss;
    this.username = username;
    this.password = password;
    this.email = email;
    this.gsm = gsm;

    this.role = role;
  }

  public UserRegistrationDto() {}

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

  public LocalDate getDateNaiss() {
    return dateNaiss;
  }

  public void setDateNaiss(LocalDate dateNaiss) {
    this.dateNaiss = dateNaiss;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getGsm() {
    return gsm;
  }

  public void setGsm(Long gsm) {
    this.gsm = gsm;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
