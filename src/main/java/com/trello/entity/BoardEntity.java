package com.trello.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.List;

@Entity

public class BoardEntity extends PanacheEntity {



    // TODO: field limitation 12.02.2023
    public String title;

    @OneToMany
    public List<UsersRolesEntity> usersRoles;

    @OneToMany
    public List<ColumnEntity> columns;
}
