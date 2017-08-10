package com.matcha.model;

import com.matcha.dao.InformationDao;
import com.matcha.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class AcountManager {

    private InformationDao infoDao;

    @Autowired
    public AcountManager(InformationDao infoDao) {
        this.infoDao = infoDao;
    }

    public JsonResponseWrapper likeUser(Integer userId, HttpSession session)
    {
        JsonResponseWrapper json = new JsonResponseWrapper();
        User author = (User)session.getAttribute("user");
        if (infoDao.likeUser(userId, author.getId()))
        {
            json.setStatus("OK");
        }
        else
            json.setStatus("Error");
        return json;
    }



}
