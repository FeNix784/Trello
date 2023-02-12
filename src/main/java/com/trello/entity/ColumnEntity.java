package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ColumnEntity extends PanacheEntity {


    @OneToMany
    public List<TaskEntity> tasks;

    public String title;
}
