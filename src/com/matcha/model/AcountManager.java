package com.matcha.model;

import com.matcha.dao.InformationDao;
import com.matcha.dao.UserDao;
import com.matcha.entity.Notification;
import com.matcha.entity.User;
import com.matcha.model.messageBroker.ImessageBroker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class AcountManager {

    private InformationDao infoDao;
    private ImessageBroker messageBroker;
    private UserDao userDao;

    @Autowired
    public AcountManager(InformationDao infoDao, ImessageBroker messageBroker, UserDao userDao) {
        this.infoDao = infoDao;
        this.messageBroker = messageBroker;
        this.userDao = userDao;
    }

    public JsonResponseWrapper likeUser(Integer userId, HttpSession session)
    {
        JsonResponseWrapper json = new JsonResponseWrapper();
        User author = (User)session.getAttribute("user");
        if (infoDao.likeUser(userId, author.getId()))
        {
            Notification notification = new Notification();
            User user = userDao.getUserById(userId);
            notification.setBody(user.getFirstName() + " " + user.getLastName() + " liked your profile!");
            notification.setCategory("like");
            json.setStatus("OK");
            messageBroker.consumeMessage(new TextMessage(notification.toString()), userId.toString(), TextSocketHandler.USER_ENDPOINT);
        }
        else
            json.setStatus("Error");
        return json;
    }

    public JsonResponseWrapper addToBlackList(Integer userId, HttpSession session)
    {
        JsonResponseWrapper json = new JsonResponseWrapper();
        User author = (User)session.getAttribute("user");
        infoDao.addUserToBlackList(author.getId(), userId);
        json.setStatus("OK");
        return json;
    }
}
