package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class UserEntity extends PanacheEntity {

    public String name;

    @OneToMany
    public List<BoardEntity> boards;
}
