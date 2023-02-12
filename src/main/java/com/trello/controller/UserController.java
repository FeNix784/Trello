package com.trello.controller;

import com.trello.entity.UserEntity;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/users")
public class UserController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        List<UserEntity> users = UserEntity.listAll();
        return Response.ok(users).build();
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(UserEntity person){
        UserEntity.persist(person);
        if(person.isPersistent()){
            return Response.created(URI.create("/users/" + person.id)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id){
        return UserEntity.findByIdOptional(id)
                .map(person -> Response.ok(person).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());

    }
}