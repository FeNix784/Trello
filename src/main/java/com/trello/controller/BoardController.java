package com.trello.controller;

import com.trello.entity.*;
import com.trello.service.UsersBoardsRolesService;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/{userID}/boards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoardController {

    @POST
    @Transactional
    public Response createBoard(BoardEntity board, @PathParam("userID") Long userID) {
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
    public Response getBoardsByUserID(@PathParam("userID") Long userID) {
        List<BoardEntity> listBoards = UsersBoardsRolesEntity.getBoardsByUserId(userID);
        if (listBoards.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(listBoards).build();
    }


    @GET
    @Path("{boardId}")
    public Response getBoardById(@PathParam("boardId")Long boardId) {
        return BoardEntity.findByIdOptional(boardId)
                .map(board -> Response.ok(board).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());

    }

    @PUT
    @Path("{boardId}")
    @Transactional
    public Response updateBoard(BoardEntity board, @PathParam("boardId")Long boardId) {
        BoardEntity entity = BoardEntity.findById(boardId);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entity.title = board.title;
        return Response.ok(Response.Status.OK).build();
    }

    @DELETE
    @Path("{boardId}")
    @Transactional
    public Response deleteBoard(@PathParam("boardId")Long boardId) {
        BoardEntity entity = BoardEntity.findById(boardId);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        UsersBoardsRolesEntity.delete("board_id", boardId);
        BoardEntity.deleteById(boardId);
        return Response.ok(Response.Status.OK).build();
    }
}
