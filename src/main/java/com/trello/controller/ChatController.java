package com.trello.controller;


import com.trello.entity.ChatEntity;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/chat")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChatController {

    @GET
    @Path("{boardId}")
    public Response getMessages(@PathParam("boardId") Long boardId, @QueryParam("limit") Integer limit) {
        List<ChatEntity> list = ChatEntity.find("from ChatEntity where boardid = ?1 order by date", boardId).range(0, limit).list();
        return Response.ok(list).build();
    }
}
