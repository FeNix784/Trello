package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class TaskEntity extends PanacheEntity {

    public String text;
}
