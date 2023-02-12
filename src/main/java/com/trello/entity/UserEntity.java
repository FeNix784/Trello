package com.trello.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class UserEntity extends PanacheEntity {

    public String login;

    //TODO: encrypt && field limitation 12.02.2023

    public String password;

    public String name;
    public String surname;

}
