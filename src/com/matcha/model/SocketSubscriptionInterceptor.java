package com.matcha.model;


import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

@Component
public class SocketSubscriptionInterceptor extends ChannelInterceptorAdapter {

    private WebsocketPermisionsList repository;

    public SocketSubscriptionInterceptor(WebsocketPermisionsList repository) {
        this.repository = repository;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);
        if (StompCommand.SUBSCRIBE.equals(header.getCommand()))
        {
            System.out.println("subscription intercept");
            if (repository.checkPermisions(header.getNativeHeader("code").get(0)))
                return message;
            else
                throw new IllegalArgumentException("evel is trying subscribe to subscription");
        }
        return message;
    }
}


