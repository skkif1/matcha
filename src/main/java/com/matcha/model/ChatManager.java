package com.matcha.model;

import com.matcha.dao.ChatDao;
import com.matcha.dao.InformationDao;
import com.matcha.entity.Conversation;
import com.matcha.entity.Message;
import com.matcha.entity.Notification;
import com.matcha.entity.User;
import com.matcha.model.messageBroker.ImessageBroker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import javax.servlet.http.HttpSession;
import java.util.*;

@Component
public class ChatManager implements IChat {

    private ChatDao chatDao;
    private ImessageBroker messageBroker;
    private InformationDao infoDao;

    @Autowired
    public ChatManager(ChatDao chatDao, ImessageBroker messageBroker, InformationDao infoDao) {
        this.chatDao = chatDao;
        this.messageBroker = messageBroker;
        this.infoDao = infoDao;
    }

    @Override
    public Boolean createConversation(Integer partnerId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        chatDao.saveConversation(partnerId, user.getId());
        return true;
    }

    @Override
    public JsonResponseWrapper getConversationList(HttpSession session) {
        JsonResponseWrapper json = new JsonResponseWrapper();
        User user = (User) session.getAttribute("user");
        List<Conversation> conversations = chatDao.getUserConversations(user);
        json.setStatus("OK");
        json.setData(conversations);
        return json;
    }


    @Override
    public Boolean sendMessage(Message message) {

        if (infoDao.checkIfMatchedWith(message.getAuthor(), message.getReciver())) {
            Integer id = chatDao.saveMessage(message);
            message = chatDao.getMessage(id);
            messageBroker.consumeMessage(new TextMessage(message.toString()), message.getConversationId().toString(),
                    TextSocketHandler.CONVERSATION_ENDPOINT);
            Notification notification = new Notification();
            notification.setCategory("message");
            notification.setBody("You have new message!");
            messageBroker.consumeMessage(new TextMessage(notification.toString()), message.getReciver().toString(),
                    TextSocketHandler.USER_ENDPOINT);
            return true;
        } else
            return false;
    }

    @Override
    public JsonResponseWrapper getConversationMessages(Integer id, Integer offset, HttpSession session) {
        JsonResponseWrapper json = new JsonResponseWrapper();
        Map<String, Object> conversationInfo = new HashMap<>();
        List<Message> convMessages = chatDao.getConversationMessages(id, offset, MESSAGE_LIMIT);
        conversationInfo.put("conv", id);
        conversationInfo.put("mess", convMessages);
        conversationInfo.put("wsacode", session.getId());
        json.setStatus("OK");
        json.setData(conversationInfo);
        return json;
    }

    @Override
    public void readAllConversationMessages(Conversation conversation, User conversationHolder) {
        chatDao.readAllConversationMessages(conversation.getId(), conversationHolder.getId());
    }
}
