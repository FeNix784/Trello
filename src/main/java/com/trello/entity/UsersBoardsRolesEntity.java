package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Sort;
import io.vertx.mutiny.ext.auth.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UsersBoardsRolesEntity extends PanacheEntity {

    @ManyToOne
    public UserEntity user;

    @ManyToOne
    public BoardEntity board;

    public Role role;


    public static List<BoardEntity>  getBoardsByUserId(Long userID){
        List<UsersBoardsRolesEntity> ubrList = list("user_id",userID);
        return ubrList.stream().map(ubr->ubr.board).collect(Collectors.toList());
    }
    public static List<UserEntity>  getUsersByBoardId(Long boardID){
        List<UsersBoardsRolesEntity> ubrList = list("board_id",boardID);;
        return ubrList.stream().map(ubr->ubr.user).collect(Collectors.toList());
    }
    public static Role getRoleByUserAndBoardId(Long userID, Long boardID){
        UsersBoardsRolesEntity ubr = find("user_id = ?1 and board_i = ?2",userID,boardID).firstResult();
        return ubr.role;
    }

}
