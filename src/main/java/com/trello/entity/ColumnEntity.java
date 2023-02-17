package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class ColumnEntity extends PanacheEntity {


    public String title;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<TaskEntity> tasks;


}
