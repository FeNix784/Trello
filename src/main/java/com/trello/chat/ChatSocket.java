package com.trello.chat;

import com.trello.chat.encoders.ChatEntityDecoder;
import com.trello.chat.encoders.ChatEntityEncoder;
import com.trello.entity.ChatEntity;
import org.eclipse.microprofile.context.ManagedExecutor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.TransactionManager;
import javax.transaction.Transactional;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;



@ServerEndpoint(value = "/chat/{boardId}/{userId}", decoders = ChatEntityDecoder.class, encoders = ChatEntityEncoder.class)
@ApplicationScoped
public class ChatSocket {

    @Inject
    ManagedExecutor managedExecutor;

    @Inject
    TransactionManager transactionManager;
    Map<Long, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("boardId") Long boardId, @PathParam("userId") Long userId) {
        sessions.put(userId, session);
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
    public void onMessage(ChatEntity chatEntity, @PathParam("boardId") Long boardId, @PathParam("userId") Long userId) {
        if (chatEntity.message.equalsIgnoreCase("_ready_")) {
            broadcast("User " + chatEntity.userId + " joined");
        } else {
            chatEntity.date = System.currentTimeMillis();
            managedExecutor.submit(() -> {
                try{
                    transactionManager.begin();
                    ChatEntity.persist(chatEntity);
                    transactionManager.commit();
                }catch(Exception e){
                    e.printStackTrace();
                }
            });
            broadcast(">> " + chatEntity.userId + ": " + chatEntity.message);
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
