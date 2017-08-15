package com.matcha.model;

import com.matcha.dao.InformationDao;
import com.matcha.dao.UserDao;
import com.matcha.entity.*;
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
        String searchedSex;
        User userWhoSearch = (User) session.getAttribute(User.USER_ATTRIBUTE_NAME);

        searchedSex = getSearchedSex(userWhoSearch);

        List<User> foundUsers = infoDao.searchUsersWith(searchedSex, userWhoSearch.getInformation().getSexPref(),
                searchParams.getMinAge(), searchParams.getMaxAge(), searchParams.getRate());

        foundUsers = foundUsers.stream().filter(user ->
        {
            if(filterSearchResult(searchParams, userWhoSearch, user))
                 return true;
            return false;
        }).collect(Collectors.toList());
        session.setAttribute("searchResult", foundUsers);
        return foundUsers;
    }

    public List<User> searchForUsers(HttpSession session)
    {
        String sex;
        Integer minAge;
        Integer maxAge;
        User user = (User) session.getAttribute(User.USER_ATTRIBUTE_NAME);

        if (user.getInformation().getSex().equals("man"))
        {
            minAge = user.getInformation().getAge() - 10;
            maxAge = user.getInformation().getAge() + 2;
        }else
        {
            minAge = user.getInformation().getAge();
            maxAge = user.getInformation().getAge() + 10;
        }

        List<User>foundUsers = infoDao.searchUsersWith(getSearchedSex(user),
                user.getInformation().getSexPref(), minAge, maxAge, 0);
        foundUsers = sortByLocation(foundUsers, user);

        return foundUsers;
    }


        private boolean filterSearchResult(SearchRequest searchRequest, User userWhoSearch, User resultUser)
    {
        DistanceCalculator.Point userWhoSearchLocation = new DistanceCalculator.Point(searchRequest.getLatitude(), searchRequest.getLongitude());
        DistanceCalculator.Point userLocation = new DistanceCalculator.Point(resultUser.getInformation().getLatitude(), resultUser.getInformation().getLongitude());

        if (searchRequest.getInterests().size() != 0)
        if (Collections.disjoint(resultUser.getInformation().getInterests(), searchRequest.getInterests()))
            return false;
        if (resultUser.getId() == userWhoSearch.getId())
            return false;
        if (distanceCalculator.calculateDistanceTo(userWhoSearchLocation, userLocation) <= searchRequest.getLocationRange())
            return false;
        return true;
    }

    private String getSearchedSex(User user)
    {
        String sex = null;
        if (user.getInformation().getSex().equals("")|| user.getInformation().getSexPref().equals(""))
            throw new IllegalArgumentException("sex or sexPrferences are not specified");
        if (user.getInformation().getSexPref().equals("'homosexual'"))
            sex = user.getInformation().getSex();
        if (user.getInformation().getSexPref().equals("heterosexual"))
            sex = (user.getInformation().getSex().equals("man")) ? "'woman'" : "'man'";
        if (user.getInformation().getSexPref().equals("bisexual"))
            sex = "'man, woman'";
        return sex;
    }

    private List<User> sortByLocation(List<User> users, User userWhoSearch)
    {
        DistanceCalculator.Point userWhoSearchLocation = new DistanceCalculator.Point(userWhoSearch.getInformation().getLatitude(),
                userWhoSearch.getInformation().getLongitude());

        users = users.stream().sorted((user1, user2) ->
        {
            DistanceCalculator.Point user1Location = new DistanceCalculator.Point(user1.getInformation().getLatitude(),
                    user1.getInformation().getLongitude());
            DistanceCalculator.Point user2Location = new DistanceCalculator.Point(user2.getInformation().getLatitude(),
                    user2.getInformation().getLongitude());
            return (int) (distanceCalculator.calculateDistanceTo(userWhoSearchLocation, user1Location) -
                                distanceCalculator.calculateDistanceTo(userWhoSearchLocation, user2Location));
        }).collect(Collectors.toList());
        return users;
    }

}
