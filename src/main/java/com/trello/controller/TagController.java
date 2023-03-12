package com.trello.controller;

import com.trello.entity.BoardEntity;
import com.trello.entity.TagEntity;
import com.trello.entity.TaskEntity;
import com.trello.entity.service.BoardService;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagController {

    @POST
    @Transactional
    public Response createTag(TagEntity tag, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        TagEntity.persist(tag);

        if (!BoardService.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if (!tag.isPersistent()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Optional<BoardEntity> board = BoardEntity.findByIdOptional(boardId);

        if (board.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        board.get().tags.add(tag);
        return Response.ok(tag).build();

    }

    @PUT
    @Path("{tagId}/pin")
    @Transactional
    public Response addTaskTag(@PathParam("tagId") Long tagId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("taskId") Long taskId) {
        if (!BoardService.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Optional<BoardEntity> board = BoardEntity.findByIdOptional(boardId);

        if (board.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).build();

        Optional<TagEntity> tag = board.get().tags.stream().filter(tagEntity -> tagEntity.id.equals(tagId)).findFirst();

        if (tag.isPresent()) {
            Optional<TaskEntity> task = TaskEntity.findByIdOptional(taskId);
            if (task.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).build();
            task.get().tags.add(tag.get());
            return Response.ok(tag).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("{tagId}")
    public Response getTagById(@PathParam("tagId") Long tagId,
                               @QueryParam("userId") Long userId,
                               @QueryParam("boardId") Long boardId) {
        if (!BoardService.isMember(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return TagEntity.findByIdOptional(tagId)
                .map(tag -> Response.ok(tag).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());

    }

    @GET
    @Path("/task")
    public Response getTagsByTaskId(@QueryParam("userId") Long userId,
                                    @QueryParam("boardId") Long boardId,
                                    @QueryParam("taskId") Long taskId) {
        if (!BoardService.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Optional<TaskEntity> task = TaskEntity.findByIdOptional(taskId);

        if (task.isPresent()) {
            Set<TagEntity> listTags = task.get().tags;
            return Response.ok(listTags).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/board")
    public Response getTagsByBoardId(@QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!BoardService.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Optional<BoardEntity> board = BoardEntity.findByIdOptional(boardId);

        if (board.isPresent()) {
            Set<TagEntity> listTags = board.get().tags;
            return Response.ok(listTags).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("{tagId}")
    @Transactional
    public Response updateTag(TagEntity tag, @PathParam("tagId") Long tagId,
                              @QueryParam("userId") Long userId,
                              @QueryParam("boardId") Long boardId) {
        if (!BoardService.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Optional<TagEntity> entity = TagEntity.findByIdOptional(tagId);

        if (entity.isPresent()) {
            entity.get().title = tag.title;
            entity.get().color = tag.color;
            return Response.ok(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{tagId}")
    @Transactional
    public Response deleteBoardTag(@PathParam("tagId") Long tagId,
                                   @QueryParam("userId") Long userId,
                                   @QueryParam("boardId") Long boardId) {
        if (!BoardService.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Optional<BoardEntity> board = BoardEntity.findByIdOptional(boardId);

        if (board.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).build();

        Set<TaskEntity> tasksOnBoard = board.get().columns.stream().flatMap(columnEntity -> columnEntity.tasks.stream()).collect(Collectors.toSet());
        tasksOnBoard.forEach(task -> task.tags.removeIf(tag -> tag.id.equals(tagId)));

        if (!board.get().tags.removeIf(tagEntity -> tagEntity.id.equals(tagId)))
            return Response.status(Response.Status.NOT_FOUND).build();
//
        return Response.ok(Response.Status.OK).build();
    }

    @DELETE
    @Path("{tagId}/unpin")
    @Transactional
    public Response deleteTaskTag(@PathParam("tagId") Long tagId,
                                  @QueryParam("userId") Long userId,
                                  @QueryParam("boardId") Long boardId,
                                  @QueryParam("taskId") Long taskId) {
        if (!BoardService.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Optional<TaskEntity> task = TaskEntity.findByIdOptional(taskId);

        if (task.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).build();

        Optional<TagEntity> tag = task.get().tags.stream().filter(tagEntity -> tagEntity.id.equals(tagId)).findFirst();

        if (tag.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).build();

        task.get().tags.removeIf(tagEntity -> tagEntity.id.equals(tagId));
        return Response.ok(Response.Status.OK).build();
    }
}
