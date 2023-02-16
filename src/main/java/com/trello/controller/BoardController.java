package com.trello.controller;

import com.trello.entity.*;
import com.trello.service.BoardsTitlesService;
import com.trello.service.UsersBoardsRolesService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/boards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoardController {

    @POST
    @Transactional
    public Response createBoard(BoardEntity board, @QueryParam("userId") Long userId) {
        BoardEntity.persist(board);
        if (board.isPersistent()) {
            UserEntity user = UserEntity.findById(userId);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return UsersBoardsRolesService.addUserRole(user, board, Role.CREATOR);
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    public Response getBoardsByUserID(@QueryParam("userId") Long userId) {
        PanacheQuery<BoardsTitlesService> boards = UsersBoardsRolesEntity.find("user_id", userId).project(BoardsTitlesService.class);
        return Response.ok(boards.list()).build();
    }

    @GET
    @Path("{boardId}")
    public Response getBoardById(@PathParam("boardId") Long boardId, @QueryParam("userId") Long userId) {
        if (UsersBoardsRolesEntity.isMember(userId, boardId)) {
            return Response.ok(BoardEntity.findById(boardId)).build();
        } else return Response.status(Response.Status.NOT_FOUND).build();

    }

    @PUT
    @Path("{boardId}")
    @Transactional
    public Response updateBoard(BoardEntity board, @PathParam("boardId") Long boardId, @QueryParam("userId") Long userId) {
        BoardEntity boardEntity = BoardEntity.findById(boardId);
        if (boardEntity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (!UsersBoardsRolesEntity.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        boardEntity.title = board.title;
        // TODO не возвращает id
        return Response.ok(board).build();
    }

    @PUT
    @Path("{boardId}/invite")
    @Transactional
    public Response addUser(@PathParam("boardId") Long boardId, @QueryParam("userId") Long userId, @QueryParam("newUserId") Long newUserId, @QueryParam("role") int role) {
        BoardEntity board = BoardEntity.findById(boardId);
        if (board == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (!UsersBoardsRolesEntity.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        UserEntity user = UserEntity.findById(newUserId);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return UsersBoardsRolesService.addUserRole(user, board, Role.returnRole(role));
    }

    @DELETE
    @Path("{boardId}")
    @Transactional
    public Response deleteBoard(@PathParam("boardId") Long boardId, @QueryParam("userId") Long userId) {
        if (UsersBoardsRolesEntity.canDelete(userId, boardId)) {
            UsersBoardsRolesEntity.delete("user_id = ?1 and board_id = ?2 and role = 0", userId, boardId);
            if (BoardEntity.deleteById(boardId)) return Response.ok(Response.Status.OK).build();
            else return Response.status(Response.Status.BAD_REQUEST).build();
        } else return Response.status(Response.Status.FORBIDDEN).build();
    }
}
