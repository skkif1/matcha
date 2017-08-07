package com.matcha.model.messageBroker;

import com.matcha.entity.Message;
import com.matcha.entity.PageContext;
import com.matcha.entity.User;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public interface ImessageBroker {

    public void addEndPoint(String endpoint);
    public void addUser(WebSocketSession session, String endpointName, String subscriptionName);
    public void removeUser(WebSocketSession session, String endpointName);
    public void consumeMessage(Message message);
    public void consumeMessage(TextMessage message, User user);

}
