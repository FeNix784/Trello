package com.trello.auth.controller;

import com.trello.auth.service.AuthService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
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
    public Response login(@QueryParam("code") String code) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "authorization_code");
        parameters.put("client_id", clientId);
        parameters.put("client_secret", clientSecret);
        parameters.put("code", code);
        Map<String, String> response = AuthService.request("https://oauth.yandex.ru/token", "POST", parameters);
        if (response == null) return Response.status(Response.Status.BAD_REQUEST).build();
        String accessToken = response.get("access_token");
        parameters = new HashMap<>();
        parameters.put("format", "json");
        parameters.put("oauth_token", accessToken);
        response = AuthService.request("https://login.yandex.ru/info", "GET", parameters);
        return Response.ok(response).build();
    }
}
