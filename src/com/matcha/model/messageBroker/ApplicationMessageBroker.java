package com.matcha.model.messageBroker;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.*;


public class ApplicationMessageBroker implements ImessageBroker {

    private Map<String, Endpoint> endpointStorage;

    public ApplicationMessageBroker() {
        this.endpointStorage = new HashMap<>(5);
    }

    public ApplicationMessageBroker(String... endPoints) {
        this.endpointStorage = new HashMap<>(5);
        for (String endPoint : endPoints) {
            this.endpointStorage.put(endPoint, new Endpoint());
        }
    }

    @Override
    public void addUser(WebSocketSession session, String endPointName, String subscriptionName) {
        addSubscription(endPointName, subscriptionName);
        List<WebSocketSession> subscribedSessions = endpointStorage.get(endPointName).getPointSubscriptions().get(subscriptionName);
        subscribedSessions.add(session);
    }

    @Override
    public void removeUser(WebSocketSession session, String subscriptionName, String endPointName) {
        Endpoint endpoint = this.endpointStorage.get(endPointName);
        List<WebSocketSession> subscribers = endpoint.getPointSubscriptions().get(subscriptionName);
        if (subscribers != null && subscribers.contains(session))
            subscribers.remove(session);
    }


    @Override
    public void consumeMessage(TextMessage message, String subscriptionName, String endPointName) {
        List<WebSocketSession> subscribers;
        Endpoint endpoint =  this.endpointStorage.get(endPointName);
        if (endpoint != null)
        {
            subscribers = endpoint.getPointSubscriptions().get(subscriptionName);
        }else
            return;
        if (subscribers != null)
        {
            subscribers.forEach((subscriber) ->
            {
                try {
                    subscriber.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void addSubscription(String endPointName, String subscriptionName) {
        Endpoint endpoint = this.endpointStorage.get(endPointName);
        if (endpoint == null)
            throw new NoSuchElementException("no endPoint with name " + endPointName);
        HashMap<String, List<WebSocketSession>> pointSubscriptions = endpoint.getPointSubscriptions();
        pointSubscriptions.putIfAbsent(subscriptionName, new ArrayList<>(10));
    }

    private static class Endpoint {
        private HashMap<String, List<WebSocketSession>> endPointSubscriptions;

        Endpoint() {
            this.endPointSubscriptions = new HashMap<>();
        }

        final HashMap<String, List<WebSocketSession>> getPointSubscriptions() {
            return this.endPointSubscriptions;
        }

        @Override
        public String toString() {
            return "Endpoint{" +
                    "endPointSubscriptions=" + endPointSubscriptions +
                    '}';
        }
    }

    @Override
    public String toString() {
        return this.endpointStorage.toString();
    }
}
