package com.trello.controller;

import com.trello.entity.LinkEntity;
import com.trello.entity.RoleEntity;
import com.trello.entity.UserEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{link}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LinkController {

    @GET
    public Response linkBoard(@PathParam("link") String link, @QueryParam("userId") Long userId) {
        LinkEntity linkEntity = LinkEntity.find("link", link).firstResult();

        if (linkEntity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        linkEntity.board.usersRoles.put(UserEntity.findById(userId), RoleEntity.findById(1));

        return Response.ok(linkEntity.board).build();
    }
}
