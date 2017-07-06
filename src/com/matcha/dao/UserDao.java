package com.matcha.dao;

import com.matcha.entity.User;

public interface UserDao {

    public Integer saveUser(User user);
    public void updateUser(User user);
    public User getUserById(Integer id);
    public User getUserByLogin(String login);
    public User getUserByEmail(String email);
}
