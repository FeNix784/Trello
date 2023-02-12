package ru.fenix784.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ColumnEntity extends PanacheEntity {

    @OneToMany
    public List<TaskEntity> tasks;
}
