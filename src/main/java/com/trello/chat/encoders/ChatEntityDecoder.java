package com.trello.chat.encoders;

import com.google.gson.Gson;
import com.trello.entity.ChatEntity;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class ChatEntityDecoder implements Decoder.Text<ChatEntity> {

    private static Gson gson = new Gson();

    @Override
    public ChatEntity decode(String s) throws DecodeException {
        return gson.fromJson(s, ChatEntity.class);
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
