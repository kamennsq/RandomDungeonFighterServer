package com.server.RandomDungeonFighter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

@Entity
public class Account extends BasicEntity{

    @NotNull
    @Column(name = "LOGIN")
    private String login;

    @NotNull
    @Column(name = "PASSWORD")
    private String password;

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
