package com.trello.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<CommentEntity> comments;

}
