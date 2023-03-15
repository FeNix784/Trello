package com.trello.controller;

import com.trello.entity.*;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{link}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LinkController {

    @PUT
    @Transactional
    public Response linkBoard(@PathParam("link") String link, @QueryParam("userId") Long userId) {
        LinkEntity linkEntity = LinkEntity.find("link", link).firstResult();

        if (linkEntity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        BoardEntity board = linkEntity.board;
        UserEntity user = UserEntity.findById(userId);
        RoleEntity role = RoleEntity.findById(1L);

        board.usersRoles.put(user, role);

        return Response.ok().build();
    }
}
