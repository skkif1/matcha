package com.matcha.dao.mysqlImpl;

import com.matcha.dao.ChatDao;
import com.matcha.dao.UserDao;
import com.matcha.entity.Conversation;
import com.matcha.entity.Message;
import com.matcha.entity.User;
import com.mysql.cj.api.mysqla.result.Resultset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import sun.nio.cs.ext.MS874;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ChatDaoImpl implements ChatDao{

    private JdbcTemplate template;
    private UserDao userDao;

    @Autowired
    public ChatDaoImpl(DataSource dataSource, UserDao dao)
    {
        this.userDao = dao;
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveMessage(Message message) {
        String sql = "INSERT INTO message (text, author, conversation_id, reciver_id) VALUES (?,?,?,?)";

        template.update(sql, message.getMessage(), message.getAuthor(), message.getConversationId(), message.getReciver());
    }

    @Override
    public Message getMessage(Integer id) {
        return null;
    }

    @Override
    public void saveConversation() {

    }

    @Override
    public List<Conversation> getUserConversations(User user)
    {
        List<Conversation> conversations = new ArrayList<>();
        String sql = "SELECT * FROM conversation WHERE user1_id = ? OR user2_id = ?";
        template.query(sql, (ResultSet rs) -> {
           Conversation conversation = new Conversation();
           conversation.setUser1(user);
           int id = rs.getInt("user1_id");
           if (user.getId() == id)
               conversation.setUser2(userDao.getUserById(rs.getInt("user2_id")));
           else
               conversation.setUser2(userDao.getUserById(id));
            conversation.setId(rs.getInt("id"));
            conversations.add(conversation);
            }, user.getId(), user.getId());
        return conversations;
    }

    @Override
    public List<Message> getConversationMessages(Integer id, Integer offset, Integer limit)
    {
        String sql = "SELECT * FROM message WHERE conversation_id = ? ORDER BY time DESC LIMIT ? OFFSET ?";
        List<Message> messages = new ArrayList<>(limit + limit / 2);
        template.query(sql, (ResultSet rs) ->
        {
            Message message = new Message();
            message.setId(rs.getInt("id"));
            message.setAuthor(rs.getInt("author"));
            message.setMessage(rs.getString("text"));
            message.setConversationId(id);
            message.setReciver(rs.getInt("reciver_id"));
            message.setTime(rs.getTimestamp("time"));
            messages.add(message);
        }, id, limit, offset);
        return messages;
    }
}
