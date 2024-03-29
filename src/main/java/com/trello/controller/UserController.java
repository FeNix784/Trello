package com.trello.controller;

import com.trello.entity.BoardEntity;
import com.trello.entity.UserEntity;
import com.trello.entity.service.BoardService;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @GET
    public Response getAllUsers() {
        List<UserEntity> users = UserEntity.listAll();
        return Response.ok(users).build();
    }

    @POST
    @Transactional
    public Response createUser(UserEntity person) {
        UserEntity.persist(person);

        if (person.isPersistent()) {
            return Response.ok(person).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") Long id) {
        return UserEntity.findByIdOptional(id)
                .map(person -> Response.ok(person).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUserById(@PathParam("id") Long id) {
        List<BoardEntity> boards = BoardService.getListBoardsByUserId(id);
        boards.forEach(board -> {

            if(BoardService.canDelete(id, board.id)){
                board.delete();
            }

            UserEntity user = UserEntity.findById(id);
            board.usersRoles.remove(user);
        });

        if (UserEntity.deleteById(id)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUserById(@PathParam("id") Long id) {

        if (UserEntity.update("id", id) == 1) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}