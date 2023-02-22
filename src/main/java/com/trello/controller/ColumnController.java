package com.trello.controller;


import com.trello.entity.BoardEntity;
import com.trello.entity.ColumnEntity;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/columns")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ColumnController {

    @POST
    @Transactional
    public Response createColumn(ColumnEntity column, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        ColumnEntity.persist(column);
        if (!BoardEntity.canChange(userId, boardId))
            return Response.status(Response.Status.BAD_REQUEST).build();
        if (column.isPersistent()) {
            BoardEntity board = BoardEntity.findById(boardId);
            if (board == null) return Response.status(Response.Status.BAD_REQUEST).build();
            column.position = board.columns.size() + 1;
            board.columns.add(column);
            return Response.ok(column).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Transactional
    @Path("{columnId}")
    public Response getColumnById(@PathParam("columnId") Long columnId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!BoardEntity.canChange(userId, boardId))
            return Response.status(Response.Status.BAD_REQUEST).build();
        return ColumnEntity.findByIdOptional(columnId).map(column -> Response.ok(column).build()).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("{columnId}")
    public Response updateColumn(ColumnEntity column, @PathParam("columnId") Long columnId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!BoardEntity.canChange(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        ColumnEntity columnEntity = ColumnEntity.findById(columnId);
        if (columnEntity == null) return Response.status(Response.Status.NOT_FOUND).build();
        columnEntity.title = column.title;
        return Response.ok(columnEntity).build();
    }

    @DELETE
    @Path("{columnId}")
    @Transactional
    public Response deleteColumn(@PathParam("columnId") Long columnId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!BoardEntity.canChange(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        BoardEntity board = BoardEntity.findById(boardId);
        board.columns.removeIf(columnEntity -> columnEntity.id.equals(columnId));
        return Response.ok(Response.Status.OK).build();
    }
}
