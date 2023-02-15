package com.trello.controller;


import com.trello.entity.BoardEntity;
import com.trello.entity.ColumnEntity;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/{userID}/boards/{boardId}/columns")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ColumnController {

    @POST
    @Transactional
    public Response createColumn(ColumnEntity column,@PathParam("userID") Long userId ,@PathParam("boardId") Long boardId){
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
