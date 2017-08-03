package com.matcha.dao.mysqlImpl;

import com.matcha.dao.ChatDao;
import com.matcha.dao.InformationDao;
import com.matcha.dao.UserDao;
import com.matcha.entity.Conversation;
import com.matcha.entity.Message;
import com.matcha.entity.User;
import com.mysql.cj.api.mysqla.result.Resultset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import sun.nio.cs.ext.MS874;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ChatDaoImpl implements ChatDao{

    private JdbcTemplate template;
    private UserDao userDao;
    private InformationDao infoDao;

    @Autowired
    public ChatDaoImpl(DataSource dataSource, UserDao dao, InformationDao infoDao)
    {
        this.userDao = dao;
        this.template = new JdbcTemplate(dataSource);
        this.infoDao = infoDao;
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
    public void saveConversation(Integer user1_id, Integer user2_id) {
        String sql = "INSERT INTO conversation (user1_id, user2_id)" +
                "  SELECT * FROM (SELECT ?, ?) AS tmp" +
                "  WHERE NOT EXISTS (" +
                "      SELECT user1_id, user2_id FROM conversation WHERE user1_id = ? AND user2_id = ?" +
                "  ) ";
        template.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, user1_id);
            statement.setInt(2, user2_id);
            statement.setInt(3, user1_id);
            statement.setInt(4, user2_id);
            return statement;
        });
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
           {
               User user2 = userDao.getUserById(id);
               user2.setInformation(infoDao.getUserInfoByUserId(id));
               conversation.setUser2(user2);
           }
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
