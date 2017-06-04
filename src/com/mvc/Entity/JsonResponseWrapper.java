package com.mvc.Entity;

import java.util.ArrayList;

public abstract class JsonResponseWrapper <T>{

    String action;
    T data;

    public JsonResponseWrapper() {
        System.out.println("json constructed");
    }

    public abstract void setAction(String action);

    public abstract String getAction();

    public abstract T getData();

    public abstract void setData(T data);
}
