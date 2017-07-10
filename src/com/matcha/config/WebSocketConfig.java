package com.matcha.config;

import com.matcha.model.SocketSubscriptionInterceptor;
import com.matcha.model.WebsocketPermisionsList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/conversation").setHandshakeHandler(new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy())).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(getSubscriptionInterceptor());
    }

    @Bean
    public SocketSubscriptionInterceptor getSubscriptionInterceptor()
    {
        return new SocketSubscriptionInterceptor(getList());
    }

    @Bean
    public WebsocketPermisionsList getList()
    {
        return new WebsocketPermisionsList();
    }

}
