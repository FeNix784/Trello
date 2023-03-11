package com.trello.controller;

import com.trello.entity.*;
import com.trello.entity.service.BoardService;
import com.trello.enums.ActivityPatterns;
import com.trello.enums.CommentType;
import com.trello.statistic.entity.StatisticEntity;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskController {

    @POST
    @Transactional
    public Response createTask(TaskEntity task,
                               @QueryParam("userId") Long userId,
                               @QueryParam("boardId") Long boardId,
                               @QueryParam("columnId") Long columnId) {

        if (!BoardService.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        task.tags = task.tags.stream().map(tag -> (TagEntity) TagEntity.findById(tag.id)).collect(Collectors.toSet());
        TaskEntity.persist(task);

        if (!task.isPersistent()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        ColumnEntity column = ColumnEntity.findById(columnId);

        if (column == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        task.position = column.tasks.size();
        column.tasks.add(task);
        UserEntity user = UserEntity.findById(userId);
        task.comments.add(new CommentEntity(String.format(ActivityPatterns.ColumnAdding.getPattern(), user.getFullName(), column.title), new Date(), user, CommentType.System));

        BoardEntity board = BoardEntity.findById(boardId);
        StatisticEntity statistic = new StatisticEntity(board,column,task, Instant.now().getEpochSecond(),0L);
        StatisticEntity.persist(statistic);

        return Response.ok(task).build();
    }

    @GET
    @Path("{taskId}")
    public Response getById(@PathParam("taskId") Long taskId, @QueryParam("userId") Long userId,
                            @QueryParam("boardId") Long boardId, @QueryParam("columnId") Long columnId) {

        if (!BoardService.isMember(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        TaskEntity task = TaskEntity.findById(taskId);

        if (task != null) {
            return Response.ok(task).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    public Response getByColumnId(@QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId,
                                  @QueryParam("columnId") Long columnId) {

        if (!BoardService.isMember(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        ColumnEntity column = ColumnEntity.findById(columnId);

        if (column == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(column.tasks).build();
    }


    @DELETE
    @Transactional
    @Path("{taskId}")
    public Response deleteById(@PathParam("taskId") Long taskId,
                               @QueryParam("userId") Long userId,
                               @QueryParam("boardId") Long boardId, @QueryParam("columnId") Long columnId) {

        if (!BoardService.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        ColumnEntity column = ColumnEntity.findById(columnId);

        if (column == null) {
            return  Response.status(Response.Status.BAD_REQUEST).build();
        }

        column.tasks.removeIf(taskEntity -> taskEntity.id.equals(taskId));
        StatisticEntity.delete("task_id",taskId);
        TaskEntity.deleteById(taskId);

        return Response.ok(column).build();
    }

    @PUT
    @Transactional
    @Path("{taskId}")
    public Response updateById(TaskEntity task, @PathParam("taskId") Long taskId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {

        if (!BoardService.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        TaskEntity taskEntity = TaskEntity.findById(taskId);

        if (taskEntity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        taskEntity.updateTask(task.text, task.description);

        return Response.ok(taskEntity).build();
    }

    @PUT
    @Transactional
    @Path("{taskId}/makers")
    public Response addMakers(@PathParam("taskId") Long taskId, @QueryParam("userId") Long userId,
                              @QueryParam("boardId") Long boardId) {

        if (!BoardService.isMember(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        TaskEntity taskEntity = TaskEntity.findById(taskId);
        UserEntity user = UserEntity.findById(userId);

        if (taskEntity == null || user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        taskEntity.makers.add(user);
        taskEntity.comments.add(new CommentEntity(String.format(ActivityPatterns.UserJoined.getPattern(), user.getFullName()), new Date(), user, CommentType.System));
        //TODO: Упростить конструктор и изменить Date на Long
        return Response.ok(taskEntity).build();
    }

    @DELETE
    @Transactional
    @Path("{taskId}/makers")
    public Response deleteMakers(@PathParam("taskId") Long taskId, @QueryParam("userId") Long userId,
                                 @QueryParam("boardId") Long boardId) {

        if (!BoardService.isMember(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        TaskEntity task = TaskEntity.findById(taskId);
        UserEntity user = UserEntity.findById(userId);

        if (task == null || user == null || !task.makers.contains(user)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        task.makers.remove(user);

        return Response.ok(task).build();
    }

    @PUT
    @Transactional
    @Path("{takId}/flip/{newPosition}")
    public Response changeTaskPositionOnBoard(@PathParam("takId") Long taskId, @PathParam("newPosition") Integer newPosition,
                                              @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId,
                                              @QueryParam("columnId") Long columnId) {

        if (!BoardService.isMember(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        ColumnEntity column = ColumnEntity.findById(columnId);
        TaskEntity task = TaskEntity.findById(taskId);

        if (column == null || task == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (task.position < newPosition) {
            column.tasks.stream().filter(taskEntity -> taskEntity.position > task.position && taskEntity.position <= newPosition).forEach(taskEntity -> taskEntity.position -= 1);
        } else {
            column.tasks.stream().filter(taskEntity -> taskEntity.position < task.position && taskEntity.position >= newPosition).forEach(taskEntity -> taskEntity.position += 1);
        }

        task.position = newPosition;

        return Response.ok(task).build();
    }

    @PUT
    @Transactional
    @Path("{taskId}/{columnId}")
    public Response dragAndDrop(@PathParam("taskId") Long taskId, @PathParam("columnId") Long columnId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("newColumnId") Long newColumnId) {

        if (!BoardService.isMember(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        ColumnEntity newColumn = ColumnEntity.findById(newColumnId);
        ColumnEntity column = ColumnEntity.findById(columnId);
        TaskEntity task = TaskEntity.findById(taskId);
        BoardEntity board = BoardEntity.findById(boardId);

        if(board == null || newColumn == null || column == null || task == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        newColumn.tasks.add(task);
        column.tasks.removeIf(taskEntity -> taskEntity.id.equals(taskId));



        StatisticEntity prevStatistic = StatisticEntity.find("task_id=?1 order by date DESC",task.id).firstResult();

        prevStatistic.duration = Instant.now().getEpochSecond() - prevStatistic.date;

        StatisticEntity statistic = new StatisticEntity(board,newColumn,task, Instant.now().getEpochSecond(),0L);
        StatisticEntity.persist(statistic);

        return Response.ok(task).build();
    }
}
