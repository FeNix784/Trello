package ru.fenix784.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class TaskEntity extends PanacheEntity {

    public String text;
}
