package com.trello.controller;


import com.trello.entity.BoardEntity;
import com.trello.entity.ColumnEntity;
import com.trello.entity.service.BoardService;
import com.trello.statistic.entity.StatisticEntity;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.IntStream;


@Path("/columns")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ColumnController {

    @POST
    @Transactional
    public Response createColumn(ColumnEntity column, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {

        ColumnEntity.persist(column);

        if (!column.isPersistent()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (!BoardService.canChange(userId, boardId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        BoardEntity board = BoardEntity.findById(boardId);

        column.position = board.columns.size() + 1;
        board.columns.add(column);

        return Response.ok(column).build();
    }

    @PUT
    @Transactional
    @Path("pos/{columnId}")
    public Response updateColumnPosition(ColumnEntity columnFromRequest, @PathParam("columnId") Long columnId,
                                         @QueryParam("userId") Long userId,
                                         @QueryParam("boardId") Long boardId) {

        ColumnEntity columnFromColumnId = ColumnEntity.findById(columnId);
        BoardEntity board = BoardEntity.findById(boardId);

        if (!BoardService.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if (columnFromRequest == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (!ColumnEntity.canReplacePosition(columnId, boardId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (columnFromRequest.position < 1 || columnFromRequest.position > board.columns.size()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        ColumnEntity columnEntityReplaced = board.columns.remove(columnFromColumnId.position - 1);
        board.columns.add(columnFromRequest.position - 1, columnEntityReplaced);
        IntStream.range(0, board.columns.size()).forEach(i -> board.columns.get(i).position = board.columns.indexOf(board.columns.get(i)) + 1);

        return Response.ok(board).build();
    }


    @GET
    @Transactional
    @Path("{columnId}")
    public Response getColumnById(@PathParam("columnId") Long columnId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {

        if (!BoardService.canChange(userId, boardId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return ColumnEntity.findByIdOptional(columnId)
                .map(column -> Response.ok(column).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Transactional
    @Path("{columnId}")
    public Response updateColumn(ColumnEntity column, @PathParam("columnId") Long columnId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {

        if (!BoardService.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        ColumnEntity columnEntity = ColumnEntity.findById(columnId);

        if (columnEntity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        columnEntity.title = column.title;

        return Response.ok(columnEntity).build();
    }

    @DELETE
    @Path("{columnId}")
    @Transactional
    public Response deleteColumn(@PathParam("columnId") Long columnId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {

        if (!BoardService.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        BoardEntity board = BoardEntity.findById(boardId);
        board.columns.removeIf(columnEntity -> columnEntity.id.equals(columnId));
        StatisticEntity.delete("column_id", columnId);

        return Response.ok(Response.Status.OK).build();
    }
}
