package com.trello.service;


import com.trello.entity.BoardEntity;
import com.trello.entity.Role;
import com.trello.entity.UserEntity;
import com.trello.entity.UsersBoardsRolesEntity;
import io.quarkus.hibernate.orm.runtime.session.JTASessionOpener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UsersBoardsRolesService {

    public static Response create(UserEntity user, BoardEntity board, Role role){
        UsersBoardsRolesEntity ubr = new UsersBoardsRolesEntity(user,board,role);
        UsersBoardsRolesEntity.persist(ubr);
        if(ubr.isPersistent()){
            return Response.created(URI.create("/"+ user.id + "/boards/" + board.id)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }



}
