package com.trello.controller;


import com.trello.entity.BoardEntity;
import com.trello.entity.CommentEntity;
import com.trello.entity.TaskEntity;
import com.trello.entity.UserEntity;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Comparator;
import java.util.stream.Collectors;


@Path("/comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentController {

    @POST
    @Transactional
    public Response createComment(CommentEntity comment, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("taskId") Long taskId) {
        if (!BoardEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        CommentEntity.persist(comment);
        if (comment.isPersistent()) {
            TaskEntity task = TaskEntity.findById(taskId);
            task.comments.add(comment);
            comment.user = UserEntity.findById(userId);
            return Response.ok(task).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    public Response getByTaskId(@QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("taskId") Long taskId) {
        if (!BoardEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        TaskEntity task = TaskEntity.findById(taskId);
        if (task != null)
            return Response.ok(task.comments.stream().sorted(Comparator.comparing(o -> o.date)).collect(Collectors.toList())).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("{commentId}")
    public Response getByCommentId(@PathParam("commentId") Long commentId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!BoardEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        CommentEntity comment = CommentEntity.findById(commentId);
        if (comment != null) return Response.ok(comment).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("{commentId}")
    @Transactional
    public Response deleteComment(@PathParam("commentId") Long commentId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        CommentEntity comment = CommentEntity.findById(commentId);
        if (!BoardEntity.isMember(userId, boardId) || !comment.user.id.equals(userId))
            return Response.status(Response.Status.FORBIDDEN).build();
        if (comment != null) {
            TaskEntity task = TaskEntity.find("select e from TaskEntity e inner join e.comments where comments_id = ?1", commentId).firstResult();
            task.comments.remove(comment);
            return Response.ok(task).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("{commentId}")
    @Transactional
    public Response updateComment(CommentEntity comment, @PathParam("commentId") Long commentId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        CommentEntity commentEntity = CommentEntity.findById(commentId);
        if (!BoardEntity.isMember(userId, boardId) || !commentEntity.user.id.equals(userId))
            return Response.status(Response.Status.FORBIDDEN).build();
        if (comment != null) {
            commentEntity.date = comment.date;
            commentEntity.text = comment.text;
            return Response.ok(commentEntity).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
