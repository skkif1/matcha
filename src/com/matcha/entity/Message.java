package com.matcha.entity;


import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.tags.EscapeBodyTag;

import java.io.IOException;
import java.sql.Timestamp;

public class Message {

    private Integer conversationId;
    private Integer id;
    private String  message;
    private Integer author;
    private Integer reciver;
    private Timestamp time;

    public Message() {
    }

    public Message(Integer conversationId, Integer id, String message, Integer author, Integer reciver, Timestamp time) {
        this.conversationId = conversationId;
        this.id = id;
        this.message = message;
        this.author = author;
        this.reciver = reciver;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Integer getReciver() {
        return reciver;
    }

    public void setReciver(Integer reciver) {
        this.reciver = reciver;
    }

    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        String res = "Message{"+ id +"}";
        try {
            res = mapper.writeValueAsString(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }
}
