package com.trello.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class TagEntity extends PanacheEntity {

    public String title;
    public String color;
}
