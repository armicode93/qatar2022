package com.example.qatar2022.dto;

import com.example.qatar2022.entities.personne.Role;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public class UserRegistrationDto {








    @Size(min = 2, max = 60 , message = "Firstname should have at least 2 characters and max 60 characters")
    @NotEmpty(message = "value is required and can t be empty")
    private String nom;

    @Size(min = 2, max = 60 , message = "LastName should have at least 2 characters and max 60 characters")
    @NotEmpty(message = "Value is required and can t be empty")
    private String prenom;



    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)

    private LocalDate dateNaiss;

   // @Size(min =4 , max = 60, message = " Username should have at least 4 characters and max 30 characters")
    @NotEmpty(message = "value is required and can t be empty")
    private String username;


    private String password;

    @Email
    @NotEmpty(message = "Value is required and can t be empty")
    private String email;

    @NumberFormat

    private Long gsm;

    private Role role;


    public UserRegistrationDto(String nom, String prenom, LocalDate dateNaiss, String username, String password, String email, Long gsm) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaiss = dateNaiss;
        this.username = username;
        this.password = password;
        this.email = email;
        this.gsm = gsm;
    }

    public UserRegistrationDto() {
    }
}




