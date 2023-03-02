package com.trello.auth.controller;


import com.trello.auth.entity.Account;
import com.trello.entity.UserEntity;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("auth")
public class AuthController {

    @POST
    @Transactional
    public Response registration(Account account){
        Account.persist(account);
        if(account.isPersistent()){
            UserEntity.persist(account);
            if (account.isPersistent()) {
                return Response.ok(account).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(account).build();
    }
}
