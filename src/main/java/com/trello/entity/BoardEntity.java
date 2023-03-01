package com.trello.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BoardEntity extends PanacheEntity {

    //TODO: field limitation 12.02.2023
    public String title;


    @OrderBy("position")
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ColumnEntity> columns = new LinkedList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<TagEntity> tags = new HashSet<>();

    @ManyToMany
    @JsonIgnore
    public Map<UserEntity, RoleEntity> usersRoles = new HashMap<>();

    public static RoleEntity getRoleByUserIdAndBoardId(Long userId, Long boardId) {
        UserEntity user = UserEntity.findById(userId);
        BoardEntity board = BoardEntity.findById(boardId);
        if (user == null || board == null) {
            return null;
        }
        return board.usersRoles.get(user);
    }

    public RoleEntity getRole(Long userId) {
        UserEntity user = UserEntity.findById(userId);
        if (user == null) {
            return null;
        }
        return this.usersRoles.get(user);

    }

    public Boolean isMember(Long userId) {
        RoleEntity roleEntity = this.getRole(userId);
        return roleEntity != null;
    }

    public static Boolean isMember(Long userId, Long boardId) {
        RoleEntity roleEntity = BoardEntity.getRoleByUserIdAndBoardId(userId, boardId);
        return roleEntity != null;
    }


    public Boolean canChange(Long userId) {
        RoleEntity roleEntity = this.getRole(userId);
        if (roleEntity != null) {
            return roleEntity.role.equals("CREATOR") || roleEntity.role.equals("ADMIN");
            }
            return false;
        }

    public static Boolean canChange(Long userId, Long boardId) {
        RoleEntity roleEntity = BoardEntity.getRoleByUserIdAndBoardId(userId, boardId);
        if (roleEntity != null) {
            return roleEntity.role.equals("CREATOR") || roleEntity.role.equals("ADMIN");
        }
        return false;
    }

    public Boolean canDelete(Long userId) {
        RoleEntity roleEntity = this.getRole(userId);
        if (roleEntity != null) {
            return roleEntity.role.equals("CREATOR");
        }
        return false;
    }
    public static Boolean canDelete(Long userId, Long boardId) {
        RoleEntity roleEntity = BoardEntity.getRoleByUserIdAndBoardId(userId, boardId);
        if (roleEntity != null) {
            return roleEntity.role.equals("CREATOR");
        }
        return false;
    }

    public Set<UserEntity> getUsers() {
        if (this.usersRoles.isEmpty()) {
            return new HashSet<>();
        }
        return this.usersRoles.keySet();
    }

    public static Set<UserEntity> getUsersByBoardId(Long boardId) {
        BoardEntity board = BoardEntity.findById(boardId);
        if (board.usersRoles.isEmpty()) {
            return new HashSet<>();
        }
        return board.usersRoles.keySet();
    }

    public static PanacheQuery<PanacheEntityBase> getBoardsByUserId(Long userId) {
        return BoardEntity.find("select e.id,e.title from BoardEntity e inner join e.usersRoles where usersroles_key = ?1", userId);
    }
    public static List<BoardEntity> getListBoardsByUserId(Long userId) {
        return BoardEntity.list("select e from BoardEntity e inner join e.usersRoles where usersroles_key = ?1", userId);
    }


}
