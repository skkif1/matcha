package com.matcha.model;


import com.matcha.dao.InformationDao;
import com.matcha.dao.mysqlImpl.InformationDaoImpl;
import com.matcha.entity.Conversation;
import com.matcha.entity.User;
import com.matcha.model.messageBroker.ImessageBroker;
import com.matcha.model.messageBroker.SockSessionDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.handler.WebSocketSessionDecorator;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class TextSocketHandler extends TextWebSocketHandler {

    private ImessageBroker messageBroker;
    @Autowired
    private InformationDao infoDao;

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

        try {
            HttpSession httpSession = (HttpSession) session.getAttributes().get("session");
            User user = (User) httpSession.getAttribute("user");
            if (session.getUri().toString().endsWith(USER_ENDPOINT)) {
                messageBroker.addUser(new SockSessionDecorator(session), USER_ENDPOINT, user.getId().toString());
                infoDao.setLastVisitTime(user.getId(), false);
            }
            if (session.getUri().toString().endsWith(CONVERSATION_ENDPOINT)) {
                messageBroker.addUser(new SockSessionDecorator(session), CONVERSATION_ENDPOINT, ((Conversation) httpSession.getAttribute("currentConversation")).getId().toString());
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        HttpSession httpSession = (HttpSession) session.getAttributes().get("session");
        try
        {
            User user = (User) httpSession.getAttribute("user");
            messageBroker.removeUser(new SockSessionDecorator(session), user.getId().toString(), USER_ENDPOINT);
            Conversation currentConversation = ((Conversation)httpSession.getAttribute("currentConversation"));
            if (currentConversation != null)
                messageBroker.removeUser(new SockSessionDecorator(session), currentConversation.getId().toString(), CONVERSATION_ENDPOINT);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
