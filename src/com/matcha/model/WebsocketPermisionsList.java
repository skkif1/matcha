package com.matcha.model;

import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.TreeSet;

public class WebsocketPermisionsList {

    private Set<String> sessions;

    public WebsocketPermisionsList() {
        this.sessions = new TreeSet<>();
    }

    protected void setPermission(String permission)
    {
        sessions.add(permission);
    }

    protected Boolean checkPermisions(String permission)
    {
        if (sessions.contains(permission))
        {
            sessions.remove(permission);
            return true;
        }
        return false;
    }
}
