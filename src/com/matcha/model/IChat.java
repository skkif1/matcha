package com.matcha.model;

import com.matcha.entity.Conversation;
import com.matcha.entity.Message;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IChat {

    Integer MESSAGE_LIMIT = 5;
    public List<Conversation> getConversations(HttpSession session);
    public Boolean sendMessage(Message message);
    public JsonResponseWrapper getConversationMessages(Integer id, Integer offset, HttpSession session);
}
