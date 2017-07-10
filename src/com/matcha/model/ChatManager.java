package com.matcha.model;

import com.matcha.dao.ChatDao;
import com.matcha.entity.Conversation;
import com.matcha.entity.Message;
import com.matcha.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChatManager implements IChat{

    private ChatDao chatDao;
    private WebsocketPermisionsList repo;

    @Autowired
    public ChatManager(ChatDao chatDao, WebsocketPermisionsList repo) {
        this.chatDao = chatDao;
        this.repo = repo;
    }

    @Override
    public List<Conversation> getConversations(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return null;
        return (ArrayList<Conversation>) chatDao.getUserConversations(user);
    }

    @Override
    public Boolean sendMessage(Message message) {
        chatDao.saveMessage(message);
        return true;
    }

    @Override
    public JsonResponseWrapper getConversationMessages(Integer id, Integer offset, HttpSession session)
    {
        JsonResponseWrapper response = new JsonResponseWrapper();
        Map<String, Object> conversationInfo = new HashMap<>();
        List<Message> convMessages = chatDao.getConversationMessages(id, offset, MESSAGE_LIMIT);
        conversationInfo.put("conv", id);
        conversationInfo.put("mess", convMessages);
        conversationInfo.put("wsacode", session.getId());
        repo.setPermission(session.getId());
        response.setStatus("OK");
        response.setData(conversationInfo);
        return response;
    }
}
