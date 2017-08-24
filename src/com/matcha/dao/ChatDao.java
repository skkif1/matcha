package com.matcha.dao;

import com.matcha.entity.Conversation;
import com.matcha.entity.Message;
import com.matcha.entity.User;

import java.util.List;

public interface ChatDao {

    public void saveMessage(Message message);
    public void updateMessage(Message message);
    public Message getMessage(Integer id);
    public void saveConversation(Integer user1_id, Integer user2_id);
    public List<Conversation> getUserConversations(User user);
    public List<Message> getConversationMessages(Integer id, Integer offset, Integer limit);
}
//
