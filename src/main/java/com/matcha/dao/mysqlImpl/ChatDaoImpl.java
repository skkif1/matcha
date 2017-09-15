package com.matcha.dao.mysqlImpl;

import com.matcha.dao.ChatDao;
import com.matcha.dao.InformationDao;
import com.matcha.dao.UserDao;
import com.matcha.entity.Conversation;
import com.matcha.entity.Message;
import com.matcha.entity.User;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
public class ChatDaoImpl implements ChatDao {

    private JdbcTemplate template;
    private UserDao userDao;
    private InformationDao infoDao;

    @Autowired
    public ChatDaoImpl(DataSource dataSource, UserDao dao, InformationDao infoDao) {
        this.userDao = dao;
        this.template = new JdbcTemplate(dataSource);
        this.infoDao = infoDao;
    }


    @Override
    public Integer saveMessage(Message message) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO message (text, author, conversation_id, reciver_id) VALUES (?,?,?,?)";
        template.update((con) ->
        {
            PreparedStatement stm = con.prepareStatement(sql, new String[]{"id"});
            stm.setString(1, message.getMessage());
            stm.setInt(2, message.getAuthor());
            stm.setInt(3, message.getConversationId());
            stm.setInt(4, message.getReciver());
            return stm;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateMessage(Message message) {
        String sql = "UPDATE message SET text = ?,  author = ?, conversation_id = ?,  reciver_id = ?, `read` = ? WHERE id = ?";
        template.update(sql, message.getMessage(), message.getAuthor(), message.getConversationId(),
                message.getReciver(), message.getRead(), message.getId());
    }

    @Override
    public Message getMessage(Integer id) {
        String sql = "SELECT * FROM message WHERE id = ?";

        final Message message = new Message();
        ;

        template.query(sql, (ResultSet rs) ->
        {
            message.setId(rs.getInt("id"));
            message.setAuthor(rs.getInt("author"));
            message.setMessage(StringEscapeUtils.escapeHtml(rs.getString("text")));
            message.setConversationId(rs.getInt("conversation_id"));
            message.setReciver(rs.getInt("reciver_id"));
            message.setTime(rs.getTimestamp("time"));
            message.setRead(rs.getBoolean("read"));
        }, id);

        return message;
    }

    @Override
    public void saveConversation(Integer user1_id, Integer user2_id) {
        String sql = "INSERT INTO conversation (user1_id, user2_id)" +
                "  SELECT * FROM (SELECT ?, ?) AS tmp" +
                "  WHERE NOT EXISTS(" +
                "      SELECT user1_id, user2_id FROM conversation WHERE (user1_id = ? AND user2_id = ?)" +
                "   OR (user1_id = ? AND user2_id = ?)) ";
        template.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, user1_id);
            statement.setInt(2, user2_id);
            statement.setInt(3, user1_id);
            statement.setInt(4, user2_id);
            statement.setInt(5, user2_id);
            statement.setInt(6, user1_id);
            return statement;
        });
    }

    @Override
    public List<Conversation> getUserConversations(User user) {
        List<Conversation> conversations = new ArrayList<>();
        String sql = "SELECT * FROM conversation WHERE user1_id = ? OR user2_id = ?";
        template.query(sql, (ResultSet rs) -> {
            Conversation conversation = new Conversation();
            conversation.setHolder(user);
            int id = rs.getInt("user1_id");
            if (user.getId() == id) {
                User partner = userDao.getUserById(rs.getInt("user2_id"));
                partner.setInformation(infoDao.getUserInfoByUserId(partner.getId()));
                conversation.setPartner(partner);
            } else {
                User partner = userDao.getUserById(id);
                partner.setInformation(infoDao.getUserInfoByUserId(id));
                conversation.setPartner(partner);
            }
            conversation.setId(rs.getInt("id"));
            conversation.setNotReadNumber(getUnreadMessagesNumber(conversation.getId(), user.getId()));
            conversations.add(conversation);
        }, user.getId(), user.getId());
        return conversations;
    }

    @Override
    public List<Message> getConversationMessages(Integer id, Integer offset, Integer limit) {
        String sql = "SELECT * FROM message WHERE conversation_id = ? ORDER BY time DESC LIMIT ? OFFSET ?";
        List<Message> messages = new ArrayList<>(limit + limit / 2);
        template.query(sql, (ResultSet rs) ->
        {
            Message message = new Message();
            message.setId(rs.getInt("id"));
            message.setAuthor(rs.getInt("author"));
            message.setMessage(StringEscapeUtils.escapeHtml(rs.getString("text")));
            message.setConversationId(id);
            message.setReciver(rs.getInt("reciver_id"));
            message.setTime(rs.getTimestamp("time"));
            message.setRead(rs.getBoolean("read"));
            messages.add(message);
        }, id, limit, offset);
        return messages;
    }

    @Override
    public void readAllConversationMessages(Integer conversationId, Integer recieverId) {
        String sql = "UPDATE message SET `read` = TRUE WHERE conversation_id = ? AND reciver_id = ?";
        template.update(sql, conversationId, recieverId);
    }

    @Override
    public Integer getAllNewMessages(Integer userId) {

        String sql = "SELECT count(*) FROM message WHERE reciver_id = ? AND `read` = FALSE";
        return template.queryForObject(sql, new Integer[]{userId}, Integer.class);
    }

    private Integer getUnreadMessagesNumber(Integer conversationId, Integer recieverId) {
        String sql = "SELECT count(*) FROM message WHERE conversation_id = ? AND reciver_id = ? AND `read` = FALSE";
        return template.queryForObject(sql, new Object[]{conversationId, recieverId}, Integer.class);
    }
}
