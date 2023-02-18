package com.trello.controller;


import com.trello.entity.*;

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
        if (!UsersBoardsRolesEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        CommentEntity.persist(comment);
        if (comment.isPersistent()) {
            TaskEntity task = TaskEntity.findById(taskId);
            task.comments.add(comment);
        }
        return Response.ok().build();
    }

    @GET
    public Response getByTaskId(@QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("taskId") Long taskId) {
        if (!UsersBoardsRolesEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        TaskEntity task = TaskEntity.findById(taskId);
        if (task != null)
            return Response.ok(task.comments.stream().sorted(Comparator.comparing(o -> o.date)).collect(Collectors.toList())).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("{commentId}")
    public Response getByCommentId(@PathParam("commentId") Long commentId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!UsersBoardsRolesEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        CommentEntity comment = CommentEntity.findById(commentId);
        if (comment != null) return Response.ok(comment).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("{commentId}")
    @Transactional
    public Response deleteComment(@PathParam("commentId") Long commentId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!UsersBoardsRolesEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        CommentEntity comment = CommentEntity.findById(commentId);
        if (comment != null) {
            comment.delete();
            return Response.ok(Response.Status.OK).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("{commentId}")
    @Transactional
    public Response updateComment(CommentEntity comment, @PathParam("commentId") Long commentId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!UsersBoardsRolesEntity.isMember(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        CommentEntity commentEntity = CommentEntity.findById(commentId);
        if (comment != null) {
            commentEntity.date = comment.date;
            commentEntity.text = comment.text;
            return Response.ok(commentEntity).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
