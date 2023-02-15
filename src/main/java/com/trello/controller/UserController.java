package com.trello.controller;

import com.trello.entity.UserEntity;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @GET
    public Response getAllUsers(){
        List<UserEntity> users = UserEntity.listAll();
        return Response.ok(users).build();
    }

    @POST
    @Transactional
    public Response createUser(UserEntity person){
        UserEntity.persist(person);
        if(person.isPersistent()){
            return Response.created(URI.create("/users/" + person.id)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") Long id){
        return UserEntity.findByIdOptional(id)
                .map(person -> Response.ok(person).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());

    }
}