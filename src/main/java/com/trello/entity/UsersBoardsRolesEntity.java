package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UsersBoardsRolesEntity extends PanacheEntity {

    @ManyToOne
    public UserEntity user;

    @ManyToOne
    public BoardEntity board;

    public Role role;
}
