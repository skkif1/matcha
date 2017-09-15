package com.matcha.model.messageBroker;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import javax.servlet.http.HttpSession;
import java.nio.file.AccessDeniedException;
import java.util.Map;

@Component
public class UserEndpointHandshakeInterceptor implements HandshakeInterceptor {


    public UserEndpointHandshakeInterceptor() {
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
        HttpSession session = serverRequest.getServletRequest().getSession();
        attributes.put("session", session);
        if (session.getAttribute("user") == null)
            throw new AccessDeniedException("access denied");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {

    }
}