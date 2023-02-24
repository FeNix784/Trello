package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class ChatEntity extends PanacheEntity {

    public Long boardId;

    public Long userId;

    public String message;

    public Date date;

    public ChatEntity(Long boardId, Long userId, String message, Date date) {
        this.boardId = boardId;
        this.userId = userId;
        this.message = message;
        this.date = date;
    }
}
