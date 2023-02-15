package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class CommentEntity extends PanacheEntity {

    public String text;

    @ManyToOne
    public TaskEntity task;

    @ManyToOne
    public UserEntity user;

}
