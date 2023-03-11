package com.trello.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
public class AuthService {

    String clientId;
    String clientSecret;
    public Map<String, String> getTokenParametersByCode(String code){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "authorization_code");
        parameters.put("client_id", clientId);
        parameters.put("client_secret", clientSecret);
        parameters.put("code", code);
        System.out.println(parameters);
        Map<String, String> response = AuthService.request("https://oauth.yandex.ru/token", "POST", parameters);

        if (response == null) {
            new ArrayList<>();
        }

        return response;
    }
    public Map<String, String> getIdentifiedInfoByToken(String token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("format", "json");
        parameters.put("oauth_token", token);
        System.out.println(token);
        Map<String, String> response = AuthService.request("https://login.yandex.ru/info", "GET", parameters);

        if (response==null || response.isEmpty()) {
            return new HashMap<>();
        }

        response.replace("default_avatar_id","https://avatars.mds.yandex.net/get-yapic/"+response.get("default_avatar_id")+"/islands-retina-small");
        return response;
    }
    public static Map<String, String> request(String link, String type, Map<String, String> parameters) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(type);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            out.flush();
            out.close();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line = rd.readLine();
            rd.close();

            return new ObjectMapper().readValue(line, HashMap.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            if (connection != null) {
                connection.disconnect();
            }

        }
    }
}
