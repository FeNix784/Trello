package com.trello.controller;


import com.trello.entity.*;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Path("/{userId}/boards/{boardId}/columns")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ColumnController {

    @POST
    @Transactional
    public Response createColumn(ColumnEntity column,@PathParam("userId") Long userId ,@PathParam("boardId") Long boardId){
        ColumnEntity.persist(column);
        if(!UsersBoardsRolesEntity.canChange(userId, boardId)){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (column.isPersistent()) {
            BoardEntity board = BoardEntity.findById(boardId);
            if (board == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            board.columns.add(column);
            return Response.ok(column).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Transactional
    @Path("{columnId}")
    public Response getColumnById(@PathParam("userId") Long userId ,
                                 @PathParam("columnId") Long columnId,
                                 @PathParam("boardId") Long boardId) {

        if(!UsersBoardsRolesEntity.canChange(userId, boardId)){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return ColumnEntity.findByIdOptional(columnId)
                .map(column -> Response.ok(column).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());

    }

    @PUT
    @Path("{columnId}")
    public Response updateColumn(ColumnEntity column, @PathParam("columnId")Long columnId) {
        ColumnEntity entity = ColumnEntity.findById(columnId);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entity.title = column.title;
        return Response.ok(entity).build();
    }
    
    @DELETE
    @Path("{columnId}")
    @Transactional
    public Response deleteColumn(@PathParam("columnId") Long columnId, @PathParam("boardId") Long boardId){
        BoardEntity board = BoardEntity.findById(boardId);
        board.columns.removeIf(columnEntity -> columnEntity.id.equals(columnId));
        return Response.ok(Response.Status.OK).build();
    }
}
