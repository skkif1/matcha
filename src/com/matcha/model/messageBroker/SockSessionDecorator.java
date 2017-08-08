package com.matcha.model.messageBroker;

import com.matcha.entity.User;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketSessionDecorator;
import javax.servlet.http.HttpSession;

public class SockSessionDecorator extends WebSocketSessionDecorator{


    public SockSessionDecorator(WebSocketSession session) {
        super(session);
    }

    /*
    * Indentety methods are implemented to store WebsocketSession only in structures similar to HashSet
    * */

    @Override
    public int hashCode() {
        HttpSession session = (HttpSession) this.getDelegate().getAttributes().get("session");
        System.out.println(((User)session.getAttribute(User.USER_ATTRIBUTE_NAME)).getId());
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WebSocketSession) || obj == null)
            return false;
        return this.hashCode() == obj.hashCode();
    }
}
