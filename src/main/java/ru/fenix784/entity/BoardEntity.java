package ru.fenix784.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class BoardEntity extends PanacheEntity {

    public String title;

    @OneToMany
    public List<UsersRolesEntity> usersRoles;

    @OneToMany
    public List<ColumnEntity> columns;
}
