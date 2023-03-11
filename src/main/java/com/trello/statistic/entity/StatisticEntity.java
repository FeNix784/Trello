package com.trello.statistic.entity;


import com.trello.entity.BoardEntity;
import com.trello.entity.ColumnEntity;
import com.trello.entity.TaskEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class StatisticEntity extends PanacheEntity {
    @ManyToOne
    public BoardEntity board;
    @ManyToOne
    public ColumnEntity column;
    @ManyToOne
    public TaskEntity task;

    public Long date;

    public Long duration;
}
