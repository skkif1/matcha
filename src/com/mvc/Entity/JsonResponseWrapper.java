package com.mvc.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class JsonResponseWrapper <T>{

    String action;
    T data;

    public JsonResponseWrapper() {

    }


    public abstract void setAction(String action);

    public abstract String getAction();

    public abstract T getData();

    public abstract void setData(T data);

}
