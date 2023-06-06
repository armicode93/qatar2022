/*package com.example.qatar2022.entities.personne;

import javax.persistence.*;

@Entity
@Table(name="role_user")
public class    RoleUser {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role ;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User  user ;

    protected RoleUser() {
    }

    public RoleUser(Role role, User user) {
        this.role = role;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "RoleUser{" +
                "id=" + id +
                ", role=" + role +
                ", user=" + user +
                '}';
    }
}

 */
