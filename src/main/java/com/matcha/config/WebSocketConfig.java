package com.matcha.config;

import com.matcha.model.TextSocketHandler;
import com.matcha.model.messageBroker.ApplicationMessageBroker;
import com.matcha.model.messageBroker.ImessageBroker;
import com.matcha.model.messageBroker.UserEndpointHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(getTextHandler(), TextSocketHandler.USER_ENDPOINT, TextSocketHandler.CONVERSATION_ENDPOINT).setHandshakeHandler(new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy()))
                .addInterceptors(new UserEndpointHandshakeInterceptor());
    }

    @Bean
    public TextWebSocketHandler getTextHandler()
    {
        return new TextSocketHandler(getMessageBroker());
    }

    @Bean
    public ImessageBroker getMessageBroker()
    {
        return new ApplicationMessageBroker(TextSocketHandler.USER_ENDPOINT, TextSocketHandler.CONVERSATION_ENDPOINT);
    }
}
