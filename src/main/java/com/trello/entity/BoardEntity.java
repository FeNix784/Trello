package com.trello.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class BoardEntity extends PanacheEntity {

    //TODO: field limitation 12.02.2023
    public String title;

    @OneToMany
    public List<ColumnEntity> columns = new CopyOnWriteArrayList<>();



}
