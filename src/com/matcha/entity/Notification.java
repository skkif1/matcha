package com.matcha.entity;


import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class Notification {

    private Integer messages;
    private Integer history;
    private String category;
    private String body;

    public Notification() {

    }

    public Integer getMessages() {
        return messages;
    }

    public void setMessages(Integer messages) {
        this.messages = messages;
    }

    public Integer getHistory() {
        return history;
    }

    public void setHistory(Integer history) {
        this.history = history;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        ObjectMapper obm = new ObjectMapper();
        String res = "Notyfication";
        try {
            res = obm.writeValueAsString(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
