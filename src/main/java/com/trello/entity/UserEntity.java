package com.trello.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserEntity extends PanacheEntity {

    public String login;

    // TODO: encrypt && field limitation 12.02.2023

    public String password;

    public String name;
    public String surname;


    @OneToMany
    public List<BoardEntity> boards;
}
