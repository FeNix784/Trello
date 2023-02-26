package com.trello.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class RoleEntity extends PanacheEntity{
    public String role;
}
