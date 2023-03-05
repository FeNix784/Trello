package com.trello.auth.controller;

import com.trello.auth.entity.Account;
import com.trello.auth.service.AuthService;
import com.trello.entity.UserEntity;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/login")
public class AuthController {

    @ConfigProperty(name = "quarkus.oauth2.client-id")
    String clientId;

    @ConfigProperty(name = "quarkus.oauth2.client-secret")
    String clientSecret;

    @GET
    @Transactional
    public Response login(@QueryParam("code") String code) {

        AuthService authService = new AuthService(clientId, clientSecret);
        String token = authService.getTokenByCode(code);
        Map<String, String> userInfo = authService.getIdentifiedInfoByToken(token);
        Account account = Account.find("token", token).firstResult();
        //TODO: 1) Удаление истекших аккаунтов
        if (account == null) {
            Account.persist(new Account(userInfo.get("default_email"), token));
            if(UserEntity.find("email",userInfo.get("default_email"))==null) {
                UserEntity.persist(new UserEntity(userInfo.get("default_email"), userInfo.get("first_name"), userInfo.get("last_name")/*, userInfo.get("default_avatar_id")*/));
                return Response.ok(UserEntity.find("email",userInfo.get("default_email")).firstResult()).build();
            }

        }
        return Response.ok(UserEntity.find("email",userInfo.get("default_email")).firstResult()).build();

    }
}
