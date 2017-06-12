package com.mvc.model;

import com.mvc.DAO.UserInformationDao;
import com.mvc.Entity.InformationBoundledUser;
import com.mvc.Entity.JsonResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInformationManager {

    private JsonResponseWrapper json;
    private UserInformationDao informationDao;

    @Autowired
    public UserInformationManager(JsonResponseWrapper json, UserInformationDao informationDao) {
        this.json = json;
        this.informationDao = informationDao;
    }

    public JsonResponseWrapper updateUser(InformationBoundledUser userInfo) {
        userInfo.setId(10);
        informationDao.saveUserInformation(userInfo);
        return json;
    }


}
