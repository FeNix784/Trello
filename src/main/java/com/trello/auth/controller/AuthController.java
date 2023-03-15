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

        Map<String,String> tokenParams = authService.getTokenParametersByCode(code);
        System.out.println(tokenParams);
        String token = tokenParams.get("access_token");


        Map<String, String> userInfo = authService.getIdentifiedInfoByToken(token);
        Long id = Long.valueOf(userInfo.get("id"));
        System.out.println(userInfo);
        Account account = Account.find("id", id).firstResult();


        if (account == null) {
            account = new Account();
            account.email = userInfo.get("default_email");
            account.token = token;
            account.yandexID = id;
            Account.persist(account);
            UserEntity.persist(new UserEntity(userInfo.get("default_email"), userInfo.get("first_name"), userInfo.get("last_name"), userInfo.get("default_avatar_id"),id));
            return Response.ok(UserEntity.find("yandexID",id).firstResult()).build();
        }

        account.token = token;
        account.email = userInfo.get("default_email");
        UserEntity user = UserEntity.find("yandexID", id).firstResult();
        user.email = userInfo.get("default_email");

        return Response.ok(user).build();

    }
}
