package com.mvc.model;

import com.mvc.Entity.User;

import java.util.List;

public interface Validator {

    public void setUser(User user);
    public Boolean validateUser(String validationLevel);
    public List<String> getErrors();
}

