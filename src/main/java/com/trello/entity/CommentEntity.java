package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class CommentEntity extends PanacheEntity {

    public String text;

    public Date date;

    @ManyToOne
    public UserEntity user;

}
