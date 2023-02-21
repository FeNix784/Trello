package com.trello.controller;

import com.trello.entity.*;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskController {


    @POST
    @Transactional
    public Response createTask(TaskEntity task, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("columnId") Long columnId) {
        if (!UsersBoardsRolesEntity.canChange(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        TaskEntity.persist(task);
        if (task.isPersistent()) {
            ColumnEntity column = ColumnEntity.findById(columnId);
            if (column == null) return Response.status(Response.Status.BAD_REQUEST).build();
            task.position = column.tasks.size();
            column.tasks.add(task);
            task.makers.add(UserEntity.findById(userId));
            return Response.ok(task).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("{taskId}")
    public Response getById(@PathParam("taskId") Long taskId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("columnId") Long columnId) {
        if (!UsersBoardsRolesEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        TaskEntity task = TaskEntity.findById(taskId);
        if (task != null) return Response.ok(task).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    public Response getByColumnId(@QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("columnId") Long columnId) {
        if (!UsersBoardsRolesEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        ColumnEntity column = ColumnEntity.findById(columnId);
        if (column != null)
            return Response.ok(column.tasks.stream().sorted(Comparator.comparingInt(o -> o.position)).toList()).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Transactional
    @Path("{taskId}")
    public Response deleteById(@PathParam("taskId") Long taskId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("columnId") Long columnId) {
        if (!UsersBoardsRolesEntity.canChange(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        ColumnEntity column = ColumnEntity.findById(columnId);
        if (column == null) return Response.status(Response.Status.BAD_REQUEST).build();
        column.tasks.removeIf(taskEntity -> taskEntity.id.equals(taskId));
        TaskEntity.deleteById(taskId);
        return Response.ok().build();
    }

    @PUT
    @Transactional
    @Path("{taskId}")
    public Response updateById(TaskEntity task, @PathParam("taskId") Long taskId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!UsersBoardsRolesEntity.canChange(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        TaskEntity taskEntity = TaskEntity.findById(taskId);
        if (taskEntity == null) return Response.status(Response.Status.NOT_FOUND).build();
        taskEntity.updateTask(task.text, task.description, task.position, task.makers, task.tags, task.comments);
        return Response.ok(taskEntity).build();
    }

    @PUT
    @Transactional
    @Path("{taskId}/makers")
    public Response addMakers(@PathParam("taskId") Long taskId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!UsersBoardsRolesEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        TaskEntity taskEntity = TaskEntity.findById(taskId);
        if (taskEntity == null) return Response.status(Response.Status.NOT_FOUND).build();
        taskEntity.makers.add(UserEntity.findById(userId));
        return Response.ok(taskEntity).build();
    }

    @PUT
    @Transactional
    @Path("{takId}/{newPosition}")
    public Response changeTaskPositionOnBoard(@PathParam("takId") Long takId, @PathParam("newPosition") Integer newPosition, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("columnId") Long columnId) {
        if (!UsersBoardsRolesEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        Optional<ColumnEntity> column = ColumnEntity.findByIdOptional(columnId);
        if (column.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        Optional<TaskEntity> task = TaskEntity.findByIdOptional(takId);
        if (task.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        if (task.get().position < newPosition) {
            column.get().tasks.stream().filter(taskEntity -> {
                if (taskEntity.position > task.get().position && taskEntity.position <= newPosition) {
                    return true;
                } else {
                    return false;
                }
            }).forEach(taskEntity -> taskEntity.position -= 1);
        } else {
            column.get().tasks.stream().filter(taskEntity -> {
                if (taskEntity.position < task.get().position && taskEntity.position >= newPosition) {
                    return true;
                } else {
                    return false;
                }
            }).forEach(taskEntity -> taskEntity.position += 1);
        }
        task.get().position = newPosition;
        return Response.ok(task).build();
    }

//    @PUT
//    @Transactional
//    @Path("{takId}/{newPosition}")
//    public Response changeTaskPositionOnBoard(@PathParam("takId") Long takId, @PathParam("newPosition") Integer newPosition, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("columnId") Long columnId) {
//        if (!UsersBoardsRolesEntity.isMember(userId, boardId))
//            return Response.status(Response.Status.FORBIDDEN).build();
//        Optional<ColumnEntity> column = ColumnEntity.findByIdOptional(columnId);
//        if (column.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
//        Optional<TaskEntity> task = TaskEntity.findByIdOptional(takId);
//        if (task.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
//        column.get().tasks.remove(task.get());
//        column.get().tasks.add(newPosition, task.get());
//        return Response.ok(task).build();
//    }
}
