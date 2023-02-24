package com.trello.chat.encoders;


import com.google.gson.Gson;
import com.trello.entity.ChatEntity;
import io.vertx.core.json.Json;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ChatEntityEncoder implements Encoder.Text<ChatEntity>{

    private static Gson gson = new Gson();

    @Override
    public String encode(ChatEntity chatEntity) throws EncodeException {
        return gson.toJson(chatEntity);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
