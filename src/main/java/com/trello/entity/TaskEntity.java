package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class TaskEntity extends PanacheEntity {

    public String text;
    public String description;

    public Integer position;

    @OneToMany
    public List<UserEntity> makers;
    @OneToMany
    public List<TagEntity> tags;

    @OneToMany
    public List<CommentEntity> comments;

}
