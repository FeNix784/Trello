package com.trello.controller;

import com.google.gson.Gson;
import com.trello.entity.*;
import com.trello.entity.service.BoardService;
import com.trello.records.BoardWithUsers;
import com.trello.records.BoardsTitlesRecord;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.apache.commons.lang3.RandomStringUtils;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

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
            if (user == null) return Response.status(Response.Status.BAD_REQUEST).build();
            board.usersRoles.put(user, RoleEntity.findById(0L));
            return Response.ok(board).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    public Response getBoardsByUserID(@QueryParam("userId") Long userId) {
        PanacheQuery<BoardsTitlesRecord> boards = BoardService.getBoardsByUserId(userId).project(BoardsTitlesRecord.class);
        return Response.ok(boards.list()).build();
    }

    @GET
    @Path("{boardId}")
    public Response getBoardById(@PathParam("boardId") Long boardId, @QueryParam("userId") Long userId) {
        BoardEntity board =  BoardEntity.findById(boardId);
        BoardWithUsers boardWithUsers = new BoardWithUsers(board.id, board.title, board.columns, board.tags, board.usersRoles);
        if (BoardService.isMember(userId, boardId)) return Response.ok(boardWithUsers).build();
        else return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Transactional
    @Path("{boardId}/link")
    public Response createLink(@PathParam("boardId") Long boardId, @QueryParam("userId") Long userId) {
        if (BoardService.canChange(userId, boardId)) {
            String shortId = RandomStringUtils.randomAlphanumeric(8);
            while (LinkEntity.find("link", shortId).firstResult() != null) shortId = RandomStringUtils.randomAlphanumeric(8);
            LinkEntity link = new LinkEntity(shortId, BoardEntity.findById(boardId));
            LinkEntity.persist(link);
            return Response.ok(link).build();
        }
        else return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @GET
    @Transactional
    @Path("{boardId}/users")
    public Response getUsers(@PathParam("boardId") Long boardId) {
        return Response.ok(BoardService.getUsersByBoardId(boardId)).build();
    }

    @PUT
    @Path("{boardId}")
    @Transactional
    public Response updateBoard(BoardEntity board, @PathParam("boardId") Long boardId, @QueryParam("userId") Long userId) {
        BoardEntity boardEntity = BoardEntity.findById(boardId);
        if (boardEntity == null) return Response.status(Response.Status.NOT_FOUND).build();
        if (!BoardService.canChange(userId,boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        boardEntity.title = board.title;
        return Response.ok(boardEntity).build();
    }

    @PUT
    @Path("{boardId}/invite")
    @Transactional
    public Response addUser(@PathParam("boardId") Long boardId, @QueryParam("userId") Long userId, @QueryParam("role") Long role) {
        if (BoardService.isMember(userId, boardId)) return Response.status(Response.Status.BAD_REQUEST).build();
        BoardEntity board = BoardEntity.findById(boardId);
        if (board == null) return Response.status(Response.Status.BAD_REQUEST).build();
        UserEntity user = UserEntity.findById(userId);
        if (user == null) return Response.status(Response.Status.BAD_REQUEST).build();
        board.usersRoles.put(user, RoleEntity.findById(role));
        return Response.ok(board).build();
    }

    @DELETE
    @Path("{boardId}")
    @Transactional
    public Response deleteBoard(@PathParam("boardId") Long boardId, @QueryParam("userId") Long userId) {
        if (BoardService.canDelete(userId, boardId)) {
            if (BoardEntity.deleteById(boardId)) return Response.ok(Response.Status.OK).build();
            else return Response.status(Response.Status.BAD_REQUEST).build();
        } else return Response.status(Response.Status.FORBIDDEN).build();

    }

    @DELETE
    @Path("{boardId}/refuse")
    @Transactional
    public Response deleteUser(@PathParam("boardId") Long boardId, @QueryParam("userId") Long userId, @QueryParam("deleteUserId") Long deleteUserId) {
        if (BoardService.canChange(userId, boardId)) {
            if (!BoardService.isMember(deleteUserId, boardId))
                return Response.status(Response.Status.BAD_REQUEST).build();
            BoardEntity board =BoardEntity.findById(boardId);
            UserEntity user = UserEntity.findById(deleteUserId);
            board.usersRoles.remove(user);
            return Response.ok(Response.Status.OK).build();
        } else return Response.status(Response.Status.FORBIDDEN).build();
    }
}
