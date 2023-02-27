package com.trello.controller;

import com.trello.entity.*;
import com.trello.enums.ActivityPatterns;
import com.trello.enums.CommentType;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskController {


    @POST
    @Transactional
    public Response createTask(TaskEntity task, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("columnId") Long columnId) {
        if (!BoardEntity.canChange(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        task.tags = task.tags.stream().map(tag -> (TagEntity) TagEntity.findById(tag.id)).collect(Collectors.toSet());
        TaskEntity.persist(task);
        if (task.isPersistent()) {
            ColumnEntity column = ColumnEntity.findById(columnId);
            if (column == null) return Response.status(Response.Status.BAD_REQUEST).build();
            task.position = column.tasks.size();
            column.tasks.add(task);
            UserEntity user = UserEntity.findById(userId);
            task.comments.add(new CommentEntity(String.format(ActivityPatterns.ColumnAdding.getPattern(), user.getFullName(), column.title), new Date(), user, CommentType.System));
            return Response.ok(task).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("{taskId}")
    public Response getById(@PathParam("taskId") Long taskId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("columnId") Long columnId) {
        if (!BoardEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        TaskEntity task = TaskEntity.findById(taskId);
        if (task != null) return Response.ok(task).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    public Response getByColumnId(@QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("columnId") Long columnId) {
        if (!BoardEntity.isMember(userId, boardId))
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
        if (!BoardEntity.canChange(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        ColumnEntity column = ColumnEntity.findById(columnId);
        if (column == null) return Response.status(Response.Status.BAD_REQUEST).build();
        column.tasks.removeIf(taskEntity -> taskEntity.id.equals(taskId));
        TaskEntity.deleteById(taskId);
        return Response.ok(column).build();
    }

    @PUT
    @Transactional
    @Path("{taskId}")
    public Response updateById(TaskEntity task, @PathParam("taskId") Long taskId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!BoardEntity.canChange(userId, boardId))
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
        if (!BoardEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        TaskEntity taskEntity = TaskEntity.findById(taskId);
        if (taskEntity == null) return Response.status(Response.Status.BAD_REQUEST).build();
        UserEntity user = UserEntity.findById(userId);
        if (user == null) return Response.status(Response.Status.BAD_REQUEST).build();
        taskEntity.makers.add(user);
        taskEntity.comments.add(new CommentEntity(String.format(ActivityPatterns.UserJoined.getPattern(), user.getFullName()), new Date(), user, CommentType.System));
        return Response.ok(taskEntity).build();
    }

    @DELETE
    @Transactional
    @Path("{taskId}/makers")
    public Response deleteMakers(@PathParam("taskId") Long taskId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!BoardEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        TaskEntity taskEntity = TaskEntity.findById(taskId);
        if (taskEntity == null) return Response.status(Response.Status.BAD_REQUEST).build();
        UserEntity user = UserEntity.findById(userId);
        if (!taskEntity.makers.contains(user)) return Response.status(Response.Status.BAD_REQUEST).build();
        taskEntity.makers.remove(user);
        return Response.ok(taskEntity).build();
    }

    @PUT
    @Transactional
    @Path("{takId}/flip/{newPosition}")
    public Response changeTaskPositionOnBoard(@PathParam("takId") Long takId, @PathParam("newPosition") Integer newPosition, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("columnId") Long columnId) {
        if (!BoardEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        Optional<ColumnEntity> column = ColumnEntity.findByIdOptional(columnId);
        if (column.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        Optional<TaskEntity> task = TaskEntity.findByIdOptional(takId);
        if (task.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        if (task.get().position < newPosition) {
            column.get().tasks.stream().filter(taskEntity -> taskEntity.position > task.get().position && taskEntity.position <= newPosition).forEach(taskEntity -> taskEntity.position -= 1);
        } else {
            column.get().tasks.stream().filter(taskEntity -> taskEntity.position < task.get().position && taskEntity.position >= newPosition).forEach(taskEntity -> taskEntity.position += 1);
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

    @PUT
    @Transactional
    @Path("{taskId}/{columnId}")
    public Response dragAndDrop(@PathParam("taskId") Long taskId, @PathParam("columnId") Long columnId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("columnId") Long newColumnId) {
        if (!BoardEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        Optional<ColumnEntity> newColumn = ColumnEntity.findByIdOptional(newColumnId);
        if (newColumn.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        Optional<ColumnEntity> column = ColumnEntity.findByIdOptional(columnId);
        if (column.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        Optional<TaskEntity> task = TaskEntity.findByIdOptional(taskId);
        if (task.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        newColumn.get().tasks.add(task.get());
        column.get().tasks.removeIf(taskEntity -> taskEntity.id.equals(taskId));
        return Response.ok(task.get()).build();
    }

}
