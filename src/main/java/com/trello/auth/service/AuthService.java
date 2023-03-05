package com.trello.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

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
