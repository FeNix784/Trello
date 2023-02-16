package com.trello.controller;

import com.trello.entity.*;
import com.trello.service.UsersBoardsRolesService;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/{userId}/boards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoardController {

    @POST
    @Transactional
    public Response createBoard(BoardEntity board, @PathParam("userId") Long userId) {

        BoardEntity.persist(board);
        if (board.isPersistent()) {
            UserEntity user = UserEntity.findById(userId);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return UsersBoardsRolesService.create(user, board, Role.CREATOR);
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    public Response getBoardsByUserID(@PathParam("userId") Long userId) {
        List<BoardEntity> listBoards = UsersBoardsRolesEntity.getBoardsByUserId(userId);
        if (listBoards.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(listBoards).build();
    }


    @GET
    @Path("{boardId}")
    public Response getBoardById(@PathParam("boardId")Long boardId,@PathParam("userId")Long userId) {
        if(UsersBoardsRolesEntity.canChange(userId,boardId)){
            return Response.ok(BoardEntity.findById(boardId)).build();
        }else return Response.status(Response.Status.NOT_FOUND).build();

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
    public Response deleteBoard(@PathParam("boardId")Long boardId, @PathParam("userId")Long userId) {
        if(UsersBoardsRolesEntity.canDelete(userId,boardId)){
            if (BoardEntity.deleteById(boardId)) return Response.ok(Response.Status.OK).build();
            else return Response.status(Response.Status.BAD_REQUEST).build();}
        else return Response.status(Response.Status.FORBIDDEN).build();
    }
}
