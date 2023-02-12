package com.trello.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;

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
        List<BoardEntity> boardEntityList = new list("user_id",userID);
        return .forEach();
    }
    public static List<UserEntity>  getUsersByBoardId(Long boardID){
        return list("board_id",boardID);
    }
}
