package com.project.revision.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String User_Role;

    @ManyToMany(mappedBy = "auths")
    private List<Client> clients;

    public Auth() {
    }

    public Auth(Long id, String user_Role, List<Client> clients) {
        this.id = id;
        User_Role = user_Role;
        this.clients = clients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_Role() {
        return User_Role;
    }

    public void setUser_Role(String user_Role) {
        User_Role = user_Role;
    }

    public List<Client> getUsers() {
        return clients;
    }

    public void setUsers(List<Client> clients) {
        this.clients = clients;
    }
}
