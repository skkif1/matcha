package com.mvc.DAO;

import com.mvc.Entity.User;

public interface UserDao {

    public boolean saveUser(User user);
    public boolean updateUser(User user);
    public User getUserByLogin(String login);
    public User getUserByEmail(String email);
}
