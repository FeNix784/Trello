package com.trello.auth.entity;

import io.quarkus.agroal.DataSource;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@DataSource("auth")
public class Account extends PanacheEntity {
    @Column(unique = true)
    public String email;
    public String password;
    public String login;
}
