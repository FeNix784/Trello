package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
public class TaskEntity extends PanacheEntity {

    public String text;
    public String description;

    public Integer position;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    public List<UserEntity> makers = new CopyOnWriteArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    public List<TagEntity> tags = new CopyOnWriteArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<CommentEntity> comments = new CopyOnWriteArrayList<>();

}
