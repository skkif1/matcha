package com.matcha.entity;

import java.util.List;

public class Conversation {

    private Integer id;
    private User user1;
    private User user2;
    private List<Message> messages;


    public Conversation() {

    }

    public Conversation(Integer id, User user1, User user2, List<Message> messages) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.messages = messages;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Conversation)
            return this.id == ((Conversation) obj).getId();
        else
            return false;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", user1=" + user1 + "\n" +
                ", user2=" + user2 + "\n" +
                ", messages=" + messages + "\n" +
                '}';
    }
}
