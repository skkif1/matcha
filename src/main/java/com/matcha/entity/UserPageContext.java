package com.matcha.entity;

public class UserPageContext {

    private boolean permissionForSearch;
    private Integer numberOfNewMessages;
    private Integer numberOfNewEvents;

    public Integer getNumberOfNewMessages() {
        return numberOfNewMessages;
    }

    public void setNumberOfNewMessages(Integer numberOfNewMessages) {
        this.numberOfNewMessages = numberOfNewMessages;
    }

    public Integer getNumberOfNewEvents() {
        return numberOfNewEvents;
    }

    public void setNumberOfNewEvents(Integer numberOfNewEvents) {
        this.numberOfNewEvents = numberOfNewEvents;
    }

    public boolean isPermissionForSearch() {
        return permissionForSearch;
    }

    public void setPermissionForSearch(boolean permissionForSearch) {
        this.permissionForSearch = permissionForSearch;
    }
}
