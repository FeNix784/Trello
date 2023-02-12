package ru.fenix784.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import ru.fenix784.entity.Role;
import ru.fenix784.entity.UserEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UsersRolesEntity extends PanacheEntity {

    @ManyToOne
    public UserEntity user;

    public Role role;
}
