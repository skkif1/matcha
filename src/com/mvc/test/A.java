package com.mvc.test;

public class A <T>{

    private String name;
    private String loll;
    private T list;

    public A(String name, String loll, T list) {
        this.name = name;
        this.loll = loll;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoll() {
        return loll;
    }

    public void setLoll(String loll) {
        this.loll = loll;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }
}
