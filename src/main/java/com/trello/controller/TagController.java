package com.trello.controller;

import com.trello.entity.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagController {

    @POST
    @Transactional
    public Response createTag(TagEntity tag, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        TagEntity.persist(tag);
        if (!UsersBoardsRolesEntity.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if (tag.isPersistent()) {
            BoardEntity board = BoardEntity.findById(boardId);
            if (board == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            board.tags.add(tag);
            return Response.ok(tag).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("{tagId}/pin")
    @Transactional
    public Response addTaskTag(@PathParam("tagId") Long tagId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("taskId") Long taskId) {
        if (!UsersBoardsRolesEntity.canChange(userId, boardId)) {
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
        if (!UsersBoardsRolesEntity.isMember(userId, boardId)) {
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
        if (!UsersBoardsRolesEntity.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Optional<TaskEntity> task = TaskEntity.findByIdOptional(taskId);
        if (task.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).build();
        Set<TagEntity> listTags = task.get().tags;
        if (listTags.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(listTags).build();

    }

    @GET
    @Path("/board")
    public Response getTagsByBoardId(@QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!UsersBoardsRolesEntity.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        BoardEntity board = BoardEntity.findById(boardId);
        Set<TagEntity> listTags = board.tags;
        if (listTags.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(listTags).build();
    }

    @PUT
    @Path("{tagId}")
    @Transactional
    public Response updateTag(TagEntity tag, @PathParam("tagId") Long tagId,
                              @QueryParam("userId") Long userId,
                              @QueryParam("boardId") Long boardId) {
        if (!UsersBoardsRolesEntity.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        TagEntity entity = TagEntity.findById(tagId);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entity.title = tag.title;
        entity.color = tag.color;
        return Response.ok(Response.Status.OK).build();
    }

    @DELETE
    @Path("{tagId}")
    @Transactional
    public Response deleteBoardTag(@PathParam("tagId") Long tagId,
                                   @QueryParam("userId") Long userId,
                                   @QueryParam("boardId") Long boardId) {
        if (UsersBoardsRolesEntity.canChange(userId, boardId)) {
            Optional<BoardEntity> board = BoardEntity.findByIdOptional(boardId);
            if (board.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).build();
            List<TaskEntity> tasksOnBoard = board.get().columns.stream().flatMap(columnEntity -> columnEntity.tasks.stream()).toList();
            tasksOnBoard.stream().map(taskEntity -> taskEntity.tags.removeIf(tagEntity -> tagEntity.id.equals(tagId)));
            if (!board.get().tags.removeIf(tagEntity -> tagEntity.id.equals(tagId))) return Response.status(Response.Status.NOT_FOUND).build();
            return Response.ok(Response.Status.OK).build();
//            if (TagEntity.deleteById(tagId)) return Response.ok(Response.Status.OK).build();
//            else return Response.status(Response.Status.BAD_REQUEST).build();
        } else return Response.status(Response.Status.FORBIDDEN).build();
    }

    @DELETE
    @Path("{tagId}/unpin")
    @Transactional
    public Response deleteTaskTag(@PathParam("tagId") Long tagId,
                                  @QueryParam("userId") Long userId,
                                  @QueryParam("boardId") Long boardId,
                                  @QueryParam("taskId") Long taskId) {
        if (UsersBoardsRolesEntity.canChange(userId, boardId)) {
            Optional<TaskEntity> task = TaskEntity.findByIdOptional(taskId);
            if (task.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).build();
            Optional<TagEntity> tag = task.get().tags.stream().filter(tagEntity -> tagEntity.id.equals(tagId)).findFirst();
            if (tag.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).build();
            task.get().tags.removeIf(tagEntity -> tagEntity.id.equals(tagId));
            return Response.ok(Response.Status.OK).build();
//            if (TagEntity.deleteById(tagId)) return Response.ok(Response.Status.OK).build();
//            else return Response.status(Response.Status.BAD_REQUEST).build();
        } else return Response.status(Response.Status.FORBIDDEN).build();
    }
}
