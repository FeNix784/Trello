package com.trello.service;

import com.trello.entity.BoardEntity;
import com.trello.entity.UserEntity;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/{userID}/boards")
public class BoardService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByUserID(@PathParam("userID") Long userID){

        UserEntity user = UserEntity.findById(userID);
        if (user != null) {
            return Response.ok(user.boards).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
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
            user.boards.add(board);
            return Response.created(URI.create("/"+ userID + "/boards/" + board.id)).build();
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
