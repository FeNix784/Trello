package com.trello.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
public class TaskEntity extends PanacheEntity {

    public String text;
    public String description;

    public Integer position;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    public List<UserEntity> makers;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    public List<TagEntity> tags;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<CommentEntity> comments;


    public void updateTask(String text, String description, Integer position, List<UserEntity> makers, List<TagEntity> tags, List<CommentEntity> comments) {
        this.text = text;
        this.description = description;
        this.position = position;
        this.makers = makers;
        this.tags = tags;
        this.comments = comments;
    }
}
