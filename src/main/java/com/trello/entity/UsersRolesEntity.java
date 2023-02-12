package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor

public class UsersRolesEntity extends PanacheEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    public UserEntity user;

    public Role role;

}
