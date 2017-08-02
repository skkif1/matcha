package com.matcha.model;

import com.matcha.dao.InformationDao;
import com.matcha.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HistoryManager {

    private InformationDao infoDao;

    @Autowired
    public HistoryManager(InformationDao infoDao) {
        this.infoDao = infoDao;
    }


    public Boolean requisterVisit(Integer visitorId, Integer userId)
    {
        if (infoDao.getUserInfoByUserId(userId) == null)
            return false;
        else
        {
            infoDao.saveVisit(visitorId, userId);
        }
        return true;
    }
}
