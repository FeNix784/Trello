package com.trello.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
public class BoardsTasksTagsEntity extends PanacheEntity {

    @ManyToOne
    public BoardEntity board;

    @ManyToOne
    public TaskEntity task;

    @ManyToOne
    public TagEntity tag;

    public BoardsTasksTagsEntity(BoardEntity board, TaskEntity task, TagEntity tag) {
        this.board = board;
        this.task = task;
        this.tag = tag;
    }

    public BoardsTasksTagsEntity(BoardEntity board, TagEntity tag) {
        this.board = board;
        this.task = null;
        this.tag = tag;
    }
}
