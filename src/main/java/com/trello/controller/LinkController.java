package com.trello.controller;

import com.trello.entity.LinkEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/{link}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LinkController {

    @GET
    public Response linkBoard(@PathParam("link") String link) {
        LinkEntity linkEntity = LinkEntity.find("link", link).firstResult();
        if (linkEntity == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(linkEntity.board).build();
    }
}
