package com.mvc.model;

import com.mvc.DAO.UserDao;
import com.mvc.DAO.UserInformationDao;
import com.mvc.Entity.JsonResponseWrapper;
import com.mvc.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInformationManager {

    private JsonResponseWrapper json;
    private UserInformationDao informationDao;
    private UserDao userDao;

    @Autowired
    public UserInformationManager(JsonResponseWrapper json, UserInformationDao informationDao, UserDao userDao) {
        this.json = json;
        this.informationDao = informationDao;
        this.userDao = userDao;
    }

    public JsonResponseWrapper updateUser(User informedUser) {

        informationDao.saveUserInformation(informedUser);
        userDao.updateUser(informedUser);
        return json;
    }
}
