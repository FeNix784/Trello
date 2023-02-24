package com.trello.chat;

import com.trello.entity.BoardEntity;
import com.trello.entity.ChatEntity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;



@ServerEndpoint("/chat/{boardId}/{userId}")
@ApplicationScoped
public class ChatSocket {

    Map<Long, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("boardId") Long boardId, @PathParam("userId") Long userId) {
        sessions.put(userId, session);
//        BoardEntity board = BoardEntity.findById(boardId);
//        List<ChatEntity> chatEntities = ChatEntity.find("boardId = ?1", boardId).list();
//        for (ChatEntity chat : chatEntities) {
//            broadcast(">> " + ChatEntity.userId + ": " + ChatEntity.message);
//        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") Long userId) {
        sessions.remove(userId);
        broadcast("User " + userId + " left");
    }

    @OnError
    public void onError(Session session, @PathParam("userId") Long userId, Throwable throwable) {
        sessions.remove(userId);
        broadcast("User " + userId + " left on error: " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("boardId") Long boardId, @PathParam("userId") Long userId) {
        if (message.equalsIgnoreCase("_ready_")) {
            broadcast("User " + userId + " joined");
        } else {
            broadcast(">> " + userId + ": " + message);
            ChatEntity chat = new ChatEntity(boardId, userId, message, System.currentTimeMillis());
            ChatEntity.persist(chat);
        }

    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }

}
