package com.matcha.model;

import com.matcha.dao.InformationDao;
import com.matcha.dao.UserDao;
import com.matcha.entity.Notification;
import com.matcha.entity.User;
import com.matcha.model.messageBroker.ImessageBroker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

@Component
public class HistoryManager {

    private InformationDao infoDao;
    private UserDao userDao;
    private ImessageBroker messageBroker;

    @Autowired
    public HistoryManager(InformationDao infoDao, UserDao userDao, ImessageBroker messageBroker) {
        this.infoDao = infoDao;
        this.userDao = userDao;
        this.messageBroker = messageBroker;
    }

    public Boolean requisterVisit(Integer visitorId, Integer userId)
    {
        if (infoDao.getUserInfoByUserId(userId) == null)
            return false;
        else
        {
            if (infoDao.saveVisit(visitorId, userId))
            {
                Notification notification = new Notification();
                User user = userDao.getUserById(userId);
                notification.setCategory("visit");
                notification.setBody(user.getFirstName() + " " + user.getLastName() + " visit your prfile!");
                messageBroker.consumeMessage(new TextMessage(notification.toString()), userId.toString(), TextSocketHandler.USER_ENDPOINT);
            }
        }
        return true;
    }
}
