package com.example.qatar2022.entities.personne;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="roles")

@ToString


public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    @NotEmpty(message = "Role value is required and can t be empty")
    @Size(min=2, max=30, message ="Role should have at least 2 characters and max 30 characters")
    @Column(name = "role")
    private String name;

    @ManyToMany
    @JoinTable(
            name="role_user", //name table de jointure
            joinColumns= @JoinColumn(name="user_id", referencedColumnName = "id"), //name column de jointure
            inverseJoinColumns= @JoinColumn(
                    name = "role_id", referencedColumnName = "id")) //name deuxieme column de jointure
    //I added referencedColumnName
    private List<User> users= new ArrayList<>();

    public Role() {

    }

    public Role(String name) {
        super();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setRoleName(String roleName) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
