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

    public static List<BoardEntity>  getBoardsByUserId(Long userID){
        List<UsersBoardsRolesEntity> ubrList = list("user_id",userID);
        return ubrList.stream().map(ubr->ubr.board).collect(Collectors.toList());
    }
    public static List<UserEntity>  getUsersByBoardId(Long boardID){
        List<UsersBoardsRolesEntity> ubrList = list("board_id",boardID);;
        return ubrList.stream().map(ubr->ubr.user).collect(Collectors.toList());
    }
    public static Role getRoleByUserAndBoardId(Long userID, Long boardID){
        UsersBoardsRolesEntity ubr = find("user_id = ?1 and board_id = ?2",userID,boardID).firstResult();
        return ubr.role;
    }
    public static Boolean canChange(Long userID, Long boardID){
        UsersBoardsRolesEntity ubr = find("user_id = ?1 and board_id = ?2",userID,boardID).firstResult();
        return ubr != null;
    }

    public static Boolean canDelete(Long userID, Long boardID){
        UsersBoardsRolesEntity ubr = find("user_id = ?1 and board_id = ?2 and role = 0",userID,boardID).firstResult();
        return ubr != null;
    }


}
