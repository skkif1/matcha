package com.matcha.model;

import com.matcha.entity.Conversation;
import com.matcha.entity.Message;
import com.matcha.entity.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IChat {

    Integer MESSAGE_LIMIT = 30;
    public Boolean createConversation(Integer parnerId, HttpSession session);
    public JsonResponseWrapper getConversationList(HttpSession session);
    public Boolean sendMessage(Message message);
    public JsonResponseWrapper getConversationMessages(Integer id, Integer offset, HttpSession session);
    public void readMessage(Integer messageId, User conversationHolder);
}
