package com.matcha.dao;

import com.matcha.entity.User;

import java.util.List;

public interface UserDao {

    public Integer saveUser(User user);
    public void updateUser(User user);
    public User getUserById(Integer id);
    public User getUserByLogin(String login);
    public User getUserByEmail(String email);
    public List<User> searchUser(Integer minAge, Integer maxAge);
    public void addFakeAcount(Integer userId);
}
