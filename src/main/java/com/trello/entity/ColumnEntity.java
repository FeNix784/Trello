package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

@Entity
public class ColumnEntity extends PanacheEntity {

    public String title;

    public Integer position;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<TaskEntity> tasks = new HashSet<>();

    public static boolean canReplacePosition(Long columnId, Long boardId){
        BoardEntity be = find("select e from BoardEntity e inner join e.columns where columns_id = ?1 and e.id = ?2",columnId, boardId).firstResult();
        return be != null;
    }
}
