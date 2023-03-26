package com.mastercard.mockapi.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class MonitoringService extends TextWebSocketHandler {
    private List<WebSocketSession> sessions = new ArrayList<>();

    public void handleMessage(String message) throws IOException {
        var textMessage = new TextMessage(message);
        for (var session: sessions) {
            session.sendMessage(textMessage);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {
        for (int i = 0; i < 10; i++) {
            session.sendMessage(new TextMessage("Message " + i));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.sessions.add(session);
    }

    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        this.sessions.remove(session);
    }
}
