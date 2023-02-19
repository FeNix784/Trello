package com.trello.controller;

import com.trello.entity.*;
import com.trello.service.BoardsTasksTagsService;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
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
        if (!UsersBoardsRolesEntity.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if (tag.isPersistent()) {
            BoardEntity board = BoardEntity.findById(boardId);
            if (board == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            BoardsTasksTagsEntity btt = new BoardsTasksTagsEntity(board, null, tag);
            return BoardsTasksTagsService.addRecord(board, null, tag);
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
//        Optional<TagEntity> tag = board.get().tags.stream().filter(tagEntity -> tagEntity.id.equals(tagId)).findFirst();
        List<BoardsTasksTagsEntity> btt = BoardsTasksTagsEntity.list("board_id = ?1", boardId);
        Optional<TagEntity> tag = btt.stream().filter(record -> record.tag.id.equals(tagId)).map(record -> record.tag).findFirst();
        if (tag.isPresent()) {
            Optional<TaskEntity> task = TaskEntity.findByIdOptional(taskId);
            if (task.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).build();
            return BoardsTasksTagsService.addRecord(board.get(), task.get(), tag.get());
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("{tagId}")
    public Response getTagById(@PathParam("tagId") Long tagId,
                               @QueryParam("userId") Long userId,
                               @QueryParam("boardId") Long boardId) {
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
        List<BoardsTasksTagsEntity> bttList = BoardsTasksTagsEntity.list("task_id = ?1", taskId);
        Set<TagEntity> listTags = bttList.stream().map(record -> record.tag).collect(Collectors.toSet());
        return Response.ok(listTags).build();

    }

    @GET
    @Path("/board")
    public Response getTagsByBoardId(@QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!UsersBoardsRolesEntity.canChange(userId, boardId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        List<BoardsTasksTagsEntity> bttList = BoardsTasksTagsEntity.list("board_id = ?1", boardId);
        Set<TagEntity> listTags = bttList.stream().map(record -> record.tag).collect(Collectors.toSet());
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
            BoardsTasksTagsEntity.delete("board_id = ?1 and tag_id = ?2", boardId, tagId);
            TagEntity.delete("id = ?1", tagId);
            return Response.ok(Response.Status.OK).build();
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
            Optional<BoardEntity> board = BoardEntity.findByIdOptional(boardId);
            if (board.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).build();
            Optional<TagEntity> tag = TagEntity.findByIdOptional(tagId);
            if (tag.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).build();
            BoardsTasksTagsEntity.delete("task_id = ?1 and tag_id = ?2", taskId, tagId);
            BoardsTasksTagsEntity.persist(new BoardsTasksTagsEntity(board.get(), null, tag.get()));
            return Response.ok(Response.Status.OK).build();
        } else return Response.status(Response.Status.FORBIDDEN).build();
    }
}
