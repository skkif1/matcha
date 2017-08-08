package com.matcha.model;


import com.matcha.entity.User;
import com.matcha.model.messageBroker.ImessageBroker;
import com.matcha.model.messageBroker.SockSessionDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.handler.WebSocketSessionDecorator;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class TextSocketHandler extends TextWebSocketHandler {

    private ArrayList<WebSocketSession> sessions = new ArrayList<>();
    private ImessageBroker messageBroker;

    public static final String USER_ENDPOINT = "/user/";
    public static final String CONVERSATION_ENDPOINT = "/conversation/";

    public TextSocketHandler() {
    }

    @Autowired
    public TextSocketHandler(ImessageBroker messageBroker) {
        this.messageBroker = messageBroker;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        HttpSession httpSession = (HttpSession) session.getAttributes().get("session");
        if (session.getUri().toString().endsWith(USER_ENDPOINT))
        {
            User user = (User) httpSession.getAttribute("user");
            messageBroker.addUser(new SockSessionDecorator(session), USER_ENDPOINT, user.getId().toString());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        HttpSession httpSession = (HttpSession) session.getAttributes().get("session");
        User user = (User) httpSession.getAttribute("user");
        messageBroker.removeUser(new SockSessionDecorator(session), user.getId().toString(), USER_ENDPOINT);
    }

}
