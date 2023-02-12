package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UsersRolesEntity extends PanacheEntity {

    @ManyToOne
    public UserEntity user;

    public Role role;
}
