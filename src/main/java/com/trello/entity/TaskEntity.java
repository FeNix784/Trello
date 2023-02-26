package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class TaskEntity extends PanacheEntity {

    public String text;
    public String description;

    public Integer position;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    public Set<UserEntity> makers = new HashSet<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    public Set<TagEntity> tags = new HashSet<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("date")
    public Set<CommentEntity> comments = new HashSet<>();

    public void updateTask(String text, String description, Integer position, Set<UserEntity> makers, Set<TagEntity> tags, Set<CommentEntity> comments) {
        this.text = text;
        this.description = description;
        this.position = position;
        this.makers = makers;
        this.tags = tags;
        this.comments = comments;
    }
}
