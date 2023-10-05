package com.example.qatar2022.entities.personne;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "roles")
@ToString
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "role")
  private List<User> users = new ArrayList<>();

  public Role() {}

  public Role(String name) {
    super();
    this.name = name;
  }

  public Role(String name, List<User> users) {
    this.name = name;
    this.users = users;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }
}
