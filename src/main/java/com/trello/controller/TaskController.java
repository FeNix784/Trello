package com.trello.controller;

import com.trello.entity.ColumnEntity;
import com.trello.entity.TaskEntity;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/{userID}/boards/{boardId}/columns")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskController {
    @POST
    @Path("{columnId}")
    @Transactional
    public Response createTask(TaskEntity task, @PathParam("columnId") Long columnId){
        TaskEntity.persist(task);
        if(task.isPersistent()){
            ColumnEntity column = ColumnEntity.findById(columnId);
            if (column == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            column.tasks.add(task);
            return Response.created(URI.create("/columns")).build();

        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
