package com.trello.service;


import com.trello.entity.BoardEntity;
import com.trello.entity.Role;
import com.trello.entity.UserEntity;
import com.trello.entity.UsersBoardsRolesEntity;

import javax.ws.rs.core.Response;

public class UsersBoardsRolesService {

    public static Response addUserRole(UserEntity user, BoardEntity board, Role role) {
        UsersBoardsRolesEntity ubr = UsersBoardsRolesEntity.find("user_id = ?1 and board_id = ?2", user, board).firstResult();
        if (ubr == null) {
            UsersBoardsRolesEntity new_ubr = new UsersBoardsRolesEntity(user, board, role);
            UsersBoardsRolesEntity.persist(new_ubr);
            if (new_ubr.isPersistent()) return Response.ok(board).build();
        } else {
            ubr.role = role;
            return Response.ok(board).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
