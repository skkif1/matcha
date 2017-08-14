package com.matcha.model;

import com.matcha.dao.InformationDao;
import com.matcha.dao.UserDao;
import com.matcha.entity.HistoryPageContext;
import com.matcha.entity.Notification;
import com.matcha.entity.SearchRequest;
import com.matcha.entity.User;
import com.matcha.model.messageBroker.ImessageBroker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.matcha.model.DistanceCalculator;

@Component
public class AcountManager {

    private InformationDao infoDao;
    private ImessageBroker messageBroker;
    private UserDao userDao;
    private DistanceCalculator distanceCalculator;

    @Autowired
    public AcountManager(InformationDao infoDao, ImessageBroker messageBroker, UserDao userDao) {
        this.infoDao = infoDao;
        this.messageBroker = messageBroker;
        this.userDao = userDao;
        this.distanceCalculator = new DistanceCalculator();
    }

    public JsonResponseWrapper likeUser(Integer userId, HttpSession session)
    {
        JsonResponseWrapper json = new JsonResponseWrapper();
        User author = (User)session.getAttribute("user");
        if (infoDao.likeUser(userId, author.getId()))
        {
            Notification notification = new Notification();
            User user = userDao.getUserById(userId);

            if (infoDao.checkIfUserLiked(author.getId(), userId))
            {
                infoDao.requisterMathedConnection(author.getId(), userId);
                notification.setCategory("matched");
                notification.setBody("now you are matched with " + user.getFirstName() + " " + user.getLastName());
                messageBroker.consumeMessage(new TextMessage(notification.toString()), userId.toString(), TextSocketHandler.USER_ENDPOINT);
            }
            notification.setBody(user.getFirstName() + " " + user.getLastName() + " liked your profile!");
            notification.setCategory("like");
            json.setStatus("OK");
            messageBroker.consumeMessage(new TextMessage(notification.toString()), userId.toString(), TextSocketHandler.USER_ENDPOINT);
        }
        else
            json.setStatus("Error");
        return json;
    }

    public JsonResponseWrapper dislikeUser(Integer userId, HttpSession session)
    {
        JsonResponseWrapper json = new JsonResponseWrapper();
        User author = (User) session.getAttribute(User.USER_ATTRIBUTE_NAME);
        infoDao.removeLike(author.getId(), userId);
        Notification notification = new Notification();
        notification.setCategory("history");
        notification.setBody("you lost connection with " + author.getFirstName() + " " + author.getLastName());
        messageBroker.consumeMessage(new TextMessage(notification.toString()), userId.toString(), TextSocketHandler.USER_ENDPOINT);
        json.setStatus("OK");
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

    public HistoryPageContext getHistoryPageContext(HttpSession session)
    {
        User user = (User) session.getAttribute(User.USER_ATTRIBUTE_NAME);
        HistoryPageContext ctx = new HistoryPageContext();
        List<User> visitors = infoDao.getUserVisitors(user.getId());
        List<User> likeAuthors = infoDao.getLikeAuthors(user.getId());
        List<User> connectdUsers = infoDao.getUserConnections(user.getId());
        ctx.setVisitors(visitors);
        ctx.setLikes(likeAuthors);
        ctx.setLastConnections(connectdUsers);
        return ctx;
    }


    public List<User> searchForUsers(SearchRequest searchParams, HttpSession session)
    {
        User userWhoSearch = (User) session.getAttribute(User.USER_ATTRIBUTE_NAME);
        List<User> foundUsers = infoDao.searchUsersWith(searchParams);

        foundUsers = foundUsers.stream().limit(2).filter(user ->
        {
            if(filterSearchResult(searchParams, userWhoSearch, user))
                 return true;
            return false;
        }).collect(Collectors.toList());
        if (foundUsers.size() > searchParams.getOffset() + 20)
            foundUsers = foundUsers.subList(searchParams.getOffset(), searchParams.getOffset() + 20);
        return foundUsers;
    }

    private boolean filterSearchResult(SearchRequest searchRequest, User userWhoSearch, User resultUser)
    {
        DistanceCalculator.Point userWhoSearchLocation = new DistanceCalculator.Point(searchRequest.getLatitude(), searchRequest.getLongitude());
        System.out.println(resultUser.getInformation());
        DistanceCalculator.Point userLocation = new DistanceCalculator.Point(resultUser.getInformation().getLatitude(), resultUser.getInformation().getLongitude());

        if (!Collections.disjoint(resultUser.getInformation().getInterests(), searchRequest.getInterests()) &&
                resultUser.getId() != userWhoSearch.getId() &&
                distanceCalculator.calculateDistanceTo(userWhoSearchLocation, userLocation) <= searchRequest.getLocationRange())
            return true;
        return false;
    }
}
