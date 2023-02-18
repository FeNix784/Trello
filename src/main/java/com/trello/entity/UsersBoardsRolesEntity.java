package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
public class UsersBoardsRolesEntity extends PanacheEntity {

    @ManyToOne
    public UserEntity user;

    @ManyToOne
    public BoardEntity board;

    public Role role;

    public UsersBoardsRolesEntity(UserEntity user, BoardEntity board, Role role) {
        this.user = user;
        this.board = board;
        this.role = role;
    }

    public static List<BoardEntity>  getBoardsByUserId(Long userId){
        List<UsersBoardsRolesEntity> ubrList = list("user_id", userId);
        return ubrList.stream().map(ubr->ubr.board).collect(Collectors.toList());
    }
    public static List<UserEntity>  getUsersByBoardId(Long boardId){
        List<UsersBoardsRolesEntity> ubrList = list("board_id", boardId);
        return ubrList.stream().map(ubr->ubr.user).collect(Collectors.toList());
    }
    public static Role getRoleByUserAndBoardId(Long userId, Long boardId){
        UsersBoardsRolesEntity ubr = find("user_id = ?1 and board_id = ?2", userId, boardId).firstResult();
        return ubr.role;
    }
    public static Boolean canChange(Long userId, Long boardId){
        UsersBoardsRolesEntity ubr = find("user_id = ?1 and board_id = ?2 and role in (0, 1)", userId, boardId).firstResult();
        return ubr != null;
    }

    public static Boolean canDelete(Long userId, Long boardId){
        UsersBoardsRolesEntity ubr = find("user_id = ?1 and board_id = ?2 and role = 0", userId, boardId).firstResult();
        return ubr != null;
    }

    public static Boolean isMember(Long userId, Long boardId){
        UsersBoardsRolesEntity ubr = find("user_id = ?1 and board_id = ?2", userId, boardId).firstResult();
        return ubr != null;
    }
}
