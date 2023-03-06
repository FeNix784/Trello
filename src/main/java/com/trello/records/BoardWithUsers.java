package com.trello.records;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trello.entity.ColumnEntity;
import com.trello.entity.RoleEntity;
import com.trello.entity.TagEntity;
import com.trello.entity.UserEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.*;


public class BoardWithUsers{
    public Long id;
    public String title;
    public List<ColumnEntity> columns;
    public Set<TagEntity> tags;
    public Set<UserEntity> users;

    public BoardWithUsers(Long id, String title,
                          List<ColumnEntity> columns,
                          Set<TagEntity> tags,
                          Map<UserEntity, RoleEntity> users) {
        this.id = id;
        this.title = title;
        this.columns = columns;
        this.tags = tags;
        this.users = users.keySet();
    }
}
