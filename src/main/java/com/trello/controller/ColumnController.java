package com.trello.controller;


import com.trello.entity.*;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/{userID}/boards/{boardId}/columns")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ColumnController {

    @POST
    @Transactional
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

    @DELETE
    @Path("{columnID}")
    @Transactional
    public Response deleteColumn(@PathParam("columnID") Long columnID, @PathParam("boardId") Long boardId){
        BoardEntity board = BoardEntity.findById(boardId);
        board.columns.removeIf(columnEntity -> columnEntity.id.equals(columnID));
        return Response.ok(Response.Status.OK).build();
    }
}
