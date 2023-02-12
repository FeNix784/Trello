package com.trello.controller;

import com.trello.entity.*;
import com.trello.service.UsersBoardsRolesService;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/{userID}/boards")
public class BoardController {

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(BoardEntity board, @PathParam("userID") Long userID) {
        BoardEntity.persist(board);
        if (board.isPersistent()) {
            UserEntity user = UserEntity.findById(userID);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return UsersBoardsRolesService.create(user, board, Role.CREATOR);

        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByUserID(@PathParam("userID") Long userID) {
        List<BoardEntity> listBoards = UsersBoardsRolesEntity.getBoardsByUserId(userID);
        if (listBoards.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(listBoards).build();
    }


    @Path("/{boardId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Response getById(@PathParam("boardId")Long boardId){
        return BoardEntity.findByIdOptional(boardId)
                .map(board -> Response.ok(board).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());

    }

    @Path("/{boardId}")
    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createColumn(ColumnEntity column, @PathParam("boardId") Long boardId, @PathParam("userID") Long userId){
        ColumnEntity.persist(column);
        if(column.isPersistent()){
            BoardEntity board = BoardEntity.findById(boardId);
            if (board == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            board.columns.add(column);
            return Response.created(URI.create("/"+ userId + "/boards/" + boardId)).build();

        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
