package com.mvc.DAO;

import com.mvc.Entity.User;

public interface UserInformationDao {

    public Boolean saveUserInformation(User user);
    public Boolean updateUserInformation(User user);
    public User getUserInformation();
}
