package com.trello.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class UserEntity extends PanacheEntity {

    @Column(unique = true)
    public String email;


    //TODO: encrypt && field limitation 12.02.2023

    public String name;
    public String surname;

    //public String avatar;


    public String getFullName() {
        return name + " " + surname;
    }

}
