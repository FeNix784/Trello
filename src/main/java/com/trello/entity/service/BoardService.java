package com.trello.entity.service;

import com.trello.entity.BoardEntity;
import com.trello.entity.RoleEntity;
import com.trello.entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BoardService {


    public static RoleEntity getRoleByUserIdAndBoardId(Long userId, Long boardId) {
        UserEntity user = UserEntity.findById(userId);
        BoardEntity board = BoardEntity.findById(boardId);
        if (user == null || board == null) {
            return null;
        }
        return board.usersRoles.get(user);
    }




    public static Boolean isMember(Long userId, Long boardId) {
        RoleEntity roleEntity = BoardService.getRoleByUserIdAndBoardId(userId, boardId);
        return roleEntity != null;
    }



    public static Boolean canChange(Long userId, Long boardId) {
        RoleEntity roleEntity = BoardService.getRoleByUserIdAndBoardId(userId, boardId);
        if (roleEntity != null) {
            return roleEntity.role.equals("CREATOR") || roleEntity.role.equals("ADMIN");
        }
        return false;
    }


    public static Boolean canDelete(Long userId, Long boardId) {
        RoleEntity roleEntity = BoardService.getRoleByUserIdAndBoardId(userId, boardId);
        if (roleEntity != null) {
            return roleEntity.role.equals("CREATOR");
        }
        return false;
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
