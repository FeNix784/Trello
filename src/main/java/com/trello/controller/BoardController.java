package com.trello.controller;

import com.trello.entity.BoardEntity;
import com.trello.entity.Role;
import com.trello.entity.UserEntity;
import com.trello.entity.UsersBoardsRolesEntity;
import com.trello.service.UsersBoardsRolesService;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Path("/{userID}/boards")
public class BoardController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByUserID(@PathParam("userID") Long userID){

        List<BoardEntity> listBoards =  UsersBoardsRolesEntity.getBoardsByUserId(userID);
        System.out.println(Arrays.toString(listBoards.toArray()));
        if (listBoards == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(listBoards).build();

    }


    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(BoardEntity board, @PathParam("userID") Long userID){
        BoardEntity.persist(board);
        if(board.isPersistent()){
            UserEntity user = UserEntity.findById(userID);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return UsersBoardsRolesService.create(user,board, Role.CREATOR);
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id){
        return BoardEntity.findByIdOptional(id)
                .map(board -> Response.ok(board).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());

    }
}
