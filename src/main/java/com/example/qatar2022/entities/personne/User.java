package com.example.qatar2022.entities.personne;



import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Entity
@Data
@Table(name="users")

@Setter
@Getter






public class User implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)


    private Long id;


    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)

    private String nom;

    @Column(nullable = false)

    private String prenom;


    @Email

    @Column(nullable = false)
    private String email;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)

    private LocalDate dateNaiss;



    @Column(nullable = false)
    private Long gsm;

    // i have to put relationship with reservation



    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


/*
    public User(String username, String password, String nom, String prenom, String email, LocalDate dateNaiss, Long gsm) {
        this.username = username;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaiss = dateNaiss;
        this.gsm = gsm;
    }

 */

    public User(String username, String password, String nom, String prenom, String email, LocalDate dateNaiss, Long gsm, Role role) {
        super();
        this.username = username;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaiss = dateNaiss;
        this.gsm = gsm;
        this.role=role;
    }

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /*public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if(this.permissions.length() > 0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }

     */

    public Long getIdUser() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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








