package com.trello.controller;

import com.trello.entity.*;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagController {

    @POST
    @Transactional
    public Response createTag(TagEntity tag, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        TagEntity.persist(tag);
        if (!UsersBoardsRolesEntity.canChange(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        if (tag.isPersistent()) {
            BoardEntity board = BoardEntity.findById(boardId);
            if (board == null) return Response.status(Response.Status.BAD_REQUEST).build();
            board.tags.add(tag);
            return Response.ok(tag).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("{tagId}")
    public Response getTagById(@PathParam("tagId") Long tagId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!UsersBoardsRolesEntity.canChange(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        return TagEntity.findByIdOptional(tagId).map(tag -> Response.ok(tag).build()).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    public Response getTagsByBoardId(@QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!UsersBoardsRolesEntity.canChange(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        BoardEntity board = BoardEntity.findById(boardId);
        Set<TagEntity> listTags = board.tags;
        if (listTags.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(listTags).build();
    }

    @PUT
    @Path("{tagId}")
    @Transactional
    public Response updateTag(TagEntity tag, @PathParam("tagId") Long tagId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (!UsersBoardsRolesEntity.canChange(userId, boardId))
            return Response.status(Response.Status.FORBIDDEN).build();
        TagEntity entity = TagEntity.findById(tagId);
        if (entity == null) return Response.status(Response.Status.NOT_FOUND).build();
        entity.title = tag.title;
        entity.color = tag.color;
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("{tagId}")
    @Transactional
    public Response deleteTag(@PathParam("tagId") Long tagId, @QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId) {
        if (UsersBoardsRolesEntity.canDelete(userId, boardId)) {
            if (TagEntity.deleteById(tagId)) return Response.ok(Response.Status.OK).build();
            else return Response.status(Response.Status.BAD_REQUEST).build();
        } else return Response.status(Response.Status.FORBIDDEN).build();
    }
}
