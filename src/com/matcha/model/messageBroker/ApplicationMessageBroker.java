package com.matcha.model.messageBroker;

import com.matcha.entity.Message;
import com.matcha.entity.PageContext;
import com.matcha.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.NativeWebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.sockjs.transport.session.WebSocketServerSockJsSession;

import java.io.IOException;
import java.util.*;


public class ApplicationMessageBroker implements ImessageBroker {

    private Map<String, Endpoint> endpointStorage;
    private static final String USER_ENDPOINT = "/user/";
    private static final String CONVERSATION_ENDPOINT = "/conversation/";


    public ApplicationMessageBroker() {
        this.endpointStorage = new HashMap<>(5);
    }

    public ApplicationMessageBroker(String... endPoints) {
        this.endpointStorage = new HashMap<>(5);

        for (String endPoint : endPoints) {
            this.endpointStorage.put(endPoint, new Endpoint());
            System.out.println("add " + endPoint);
        }
    }


    @Override
    public void addEndPoint(String endpoint) {
        Endpoint endpointNode = new Endpoint();
        this.endpointStorage.put(endpoint, endpointNode);
    }

    @Override
    public void addUser(WebSocketSession session, String endPointName, String subscriptionName) {
        addSubscription(endPointName, subscriptionName);
        HashSet<WebSocketSession> subscribedSessions = endpointStorage.get(endPointName).getPointSubscriptions().get(subscriptionName);
        subscribedSessions.add(session);
    }

    @Override
    public void removeUser(WebSocketSession session, String endPointName) {

    }

    @Override
    public void consumeMessage(Message message) {

    }

    @Override
    public void consumeMessage(TextMessage message, User user) {
        Endpoint endpoint = this.endpointStorage.get(USER_ENDPOINT);
        HashMap map = endpoint.getPointSubscriptions();
        HashSet<WebSocketSession> userSubscription = (HashSet<WebSocketSession>) map.get(user.getId().toString());
        userSubscription.forEach((session) ->
        {
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void addSubscription(String endPointName, String subscriptionName) {
        Endpoint endpoint = this.endpointStorage.get(endPointName);
        if (endpoint == null)
            throw new NoSuchElementException("no endPoint with name " + endPointName);
        HashMap<String, HashSet<WebSocketSession>> pointSubscriptions = endpoint.getPointSubscriptions();
        pointSubscriptions.putIfAbsent(subscriptionName, new HashSet<>());
    }

    private static class Endpoint {
        private HashMap<String, HashSet<WebSocketSession>> endPointSubscriptions;

        Endpoint() {
            this.endPointSubscriptions = new HashMap<>();
        }

        final HashMap<String, HashSet<WebSocketSession>> getPointSubscriptions() {
            return this.endPointSubscriptions;
        }
    }

    @Override
    public String toString() {
        return this.endpointStorage.toString();
    }
}
