package com.matcha.config;

import com.matcha.model.TextSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.ArrayList;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(getTextHandler(), "/name").setHandshakeHandler(new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy()));
        System.out.println(webSocketHandlerRegistry);
    }



    @Bean
    public TextWebSocketHandler getTextHandler()
    {
        System.out.println("create handler");
        return new TextSocketHandler();
    }
}
