package com.trello.entity;

import com.trello.enums.CommentType;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity extends PanacheEntity {

    public String text;

    public Date date;

    @ManyToOne
    public UserEntity user;

    public CommentType type;

}
