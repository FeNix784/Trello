package com.trello.auth.entity;

import io.quarkus.agroal.DataSource;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@DataSource("auth")
@AllArgsConstructor
@NoArgsConstructor
public class Account extends PanacheEntity {
    @Column(unique = true)
    public String email;
    public String token;
    //public Long createDate;
    //public Integer lifeTime;


}
