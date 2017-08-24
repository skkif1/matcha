package com.matcha.entity;

import java.util.List;

public class Conversation {

    private Integer id;
    private User holder;
    private User partner;
    private Integer notReadNumber;
    private List<Message> messages;


    public Conversation() {

    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getHolder() {
        return holder;
    }

    public void setHolder(User holderId) {
        this.holder = holderId;
    }

    public User getPartner() {
        return partner;
    }

    public void setPartner(User partner) {
        this.partner = partner;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Integer getNotReadNumber() {
        return notReadNumber;
    }

    public void setNotReadNumber(Integer notReadNumber) {
        this.notReadNumber = notReadNumber;
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
                ", holder=" + holder +
                ", partner=" + partner +
                ", notReadNumber=" + notReadNumber +
                ", messages=" + messages +
                '}';
    }
}
