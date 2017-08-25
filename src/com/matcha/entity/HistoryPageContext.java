package com.matcha.entity;

import java.util.List;

public class HistoryPageContext {

    private Integer newLikes;
    private Integer newVisitors;
    private Integer newConnections;

    private List<User> likes;
    private List<User> visited;
    private List<User> visitors;
    private List<User> lastConnections;

    public HistoryPageContext() {
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public List<User> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<User> visitors) {
        this.visitors = visitors;
    }

    public List<User> getLastConnections() {
        return lastConnections;
    }

    public void setLastConnections(List<User> lastConnections) {
        this.lastConnections = lastConnections;
    }

    public List<User> getVisited() {
        return visited;
    }

    public void setVisited(List<User> visited) {
        this.visited = visited;
    }

    public Integer getNewLikes() {
        return newLikes;
    }

    public void setNewLikes(Integer newLikes) {
        this.newLikes = newLikes;
    }

    public Integer getNewVisitors() {
        return newVisitors;
    }

    public void setNewVisitors(Integer newVisitors) {
        this.newVisitors = newVisitors;
    }

    public Integer getNewConnections() {
        return newConnections;
    }

    public void setNewConnections(Integer newConnections) {
        this.newConnections = newConnections;
    }
}
