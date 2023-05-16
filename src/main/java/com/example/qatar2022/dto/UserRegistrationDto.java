package com.example.qatar2022.dto;

import com.example.qatar2022.entities.personne.Role;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;



public class UserRegistrationDto {








    @Size(min = 3, max = 60 , message = "Firstname should have at least 3 characters and max 60 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only Alphabet characters")
    @NotEmpty(message="Nom value is required and can t be empty")
    private String nom;

    @Size(min = 3, max = 60, message= "LastName should have at least 3 characters and max 60 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only Alphabet characters")
    @NotEmpty(message = "Value is required and can t be empty")

    private String prenom;



    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)


    private LocalDate dateNaiss;

    @Size(min =4 , max = 60,message= "Username should have at least 3 characters and max 60 characters")
    @NotEmpty(message = "Value is required and can t be empty")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",message="Password should contain at least one lowercase letter, one uppercase letter, and one digit")
    @Size(min=4,max=15, message= "Password should have at least 4 characters and max 15 characters")
    private String password;

    @Email(message = "Insert email ")
    @NotNull(message= "Value is required and can t be empty")
    private String email;

    @NumberFormat

    @NotNull(message= "value is required and can t be empty")
    private Long gsm;

    private Role role;

    public UserRegistrationDto(String nom, String prenom, LocalDate dateNaiss, String username, String password, String email, Long gsm,Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaiss = dateNaiss;
        this.username = username;
        this.password = password;
        this.email = email;
        this.gsm = gsm;

        this.role= role;

    }



    public UserRegistrationDto() {
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




