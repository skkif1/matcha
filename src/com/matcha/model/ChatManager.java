package com.matcha.model;

import com.matcha.dao.ChatDao;
import com.matcha.entity.Conversation;
import com.matcha.entity.Message;
import com.matcha.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChatManager implements IChat{

    private ChatDao chatDao;

    @Autowired
    public ChatManager(ChatDao chatDao) {
        this.chatDao = chatDao;
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
    public JsonResponseWrapper getConversationMessages(Integer id, Integer offset)
    {
        JsonResponseWrapper response = new JsonResponseWrapper();
        List<Message> convMessages = chatDao.getConversationMessages(id, offset, MESSAGE_LIMIT);
        response.setStatus("OK");
        response.setData(convMessages);
        return response;
    }
}
