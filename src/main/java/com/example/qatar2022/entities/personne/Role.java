package com.example.qatar2022.entities.personne;

//public enum Role implements GrantedAuthority {

public enum Role  {
    ROLE_ADMIN, ROLE_CLIENT;

    public String getAuthority() {
        return name();
    }
}

