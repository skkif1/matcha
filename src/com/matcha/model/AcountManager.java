package com.matcha.model;

import com.matcha.dao.InformationDao;
import com.matcha.dao.UserDao;
import com.matcha.entity.*;
import com.matcha.model.messageBroker.ImessageBroker;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
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

    public JsonResponseWrapper likeUser(Integer userId, HttpSession session) {
        JsonResponseWrapper json = new JsonResponseWrapper();
        User author = (User) session.getAttribute("user");
        if (infoDao.likeUser(userId, author.getId())) {
            Notification notification = new Notification();
            User user = userDao.getUserById(userId);

            if (infoDao.checkIfUserLiked(author.getId(), userId)) {
                infoDao.requisterMathedConnection(author.getId(), userId);
                notification.setCategory("matched");
                notification.setBody("now you are matched with " + user.getFirstName() + " " + user.getLastName());
                messageBroker.consumeMessage(new TextMessage(notification.toString()), userId.toString(), TextSocketHandler.USER_ENDPOINT);
            }
            notification.setBody(user.getFirstName() + " " + user.getLastName() + " liked your profile!");
            notification.setCategory("like");
            json.setStatus("OK");
            messageBroker.consumeMessage(new TextMessage(notification.toString()), userId.toString(), TextSocketHandler.USER_ENDPOINT);
        } else
            json.setStatus("Error");
        return json;
    }

    public JsonResponseWrapper dislikeUser(Integer userId, HttpSession session) {
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

    public JsonResponseWrapper addToBlackList(Integer userId, HttpSession session) {
        JsonResponseWrapper json = new JsonResponseWrapper();
        User author = (User) session.getAttribute("user");
        infoDao.addUserToBlackList(author.getId(), userId);
        json.setStatus("OK");
        return json;
    }

    public Boolean requisterVisit(Integer visitorId, Integer userId) {
        if (infoDao.getUserInfoByUserId(userId) == null)
            return false;
        else {
            if (infoDao.saveVisit(visitorId, userId)) {
                Notification notification = new Notification();
                User user = userDao.getUserById(userId);
                notification.setCategory("visit");
                notification.setBody(user.getFirstName() + " " + user.getLastName() + " visit your prfile!");
                messageBroker.consumeMessage(new TextMessage(notification.toString()), userId.toString(), TextSocketHandler.USER_ENDPOINT);
            }
        }
        return true;
    }


    public HistoryPageContext getHistoryPageContext(HttpSession session) {
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

    public Boolean checkIfUserEligableForSearch(User user)
    {
        UserInformation info = infoDao.getUserInfoByUserId(user.getId());
        if (info == null || info.getSex() == null || info.getAvatar() == null)
            return false;
        return true;
    }



    public List<User> searchForUsers(SearchRequest searchParams, HttpSession session) {
        String searchedSex;
        User userWhoSearch = (User) session.getAttribute(User.USER_ATTRIBUTE_NAME);

        System.out.println(searchParams);
        searchedSex = getSearchedSex(userWhoSearch);
        List<User> foundUsers = infoDao.searchUsersWith(searchedSex, userWhoSearch.getInformation().getSexPref(),
                searchParams.getMinAge(), searchParams.getMaxAge(), searchParams.getRate());
        foundUsers = foundUsers.stream().filter(user ->
        {
            if (filterSearchResult(searchParams, userWhoSearch, user))
                return true;
            return false;
        }).collect(Collectors.toList());
        session.setAttribute("searchResult", foundUsers);
        return foundUsers;
    }

    public List<User> searchForUsers(HttpSession session) {
        Integer minAge;
        Integer maxAge;
        Integer rate;
        User user = (User) session.getAttribute(User.USER_ATTRIBUTE_NAME);

        if (user.getInformation().getSex().equals("man")) {
            minAge = user.getInformation().getAge() - 10;
            maxAge = user.getInformation().getAge() + 2;
            rate = user.getInformation().getRate() - 5;
        } else {
            minAge = user.getInformation().getAge();
            maxAge = user.getInformation().getAge() + 10;
            rate = user.getInformation().getRate() - 1;
        }

        List<User> foundUsers = infoDao.searchUsersWith(getSearchedSex(user),
                user.getInformation().getSexPref(), minAge, maxAge, rate);

        foundUsers = foundUsers.stream().filter(user1 ->
        {
            if (infoDao.checkIfUserVisit(user.getId(), user1.getId()) || infoDao.checkIfUserVisit(user1.getId(), user.getId()))
                return false;
            return true;
        }).collect(Collectors.toList());
        return foundUsers;
    }

    public List<User> sortSuggestedUsers(List<User> suggestedUsers, User userWhoSearch, String sortType)
    {
        List<User> res;

        switch (sortType)
        {
            case "age":
                res = UserSearchFiltrator.sortByAge(suggestedUsers);
                break;
            case "location":
                res = UserSearchFiltrator.sortByLocation(suggestedUsers, userWhoSearch);
                break;
            case "rating":
                res = UserSearchFiltrator.sortByRate(suggestedUsers);
                break;
            case "interests":
                res = UserSearchFiltrator.sortByTags(suggestedUsers, userWhoSearch);
                break;
            default:
                    res = suggestedUsers;
        }
        return res;
    }


    public List<User> filterSuggestedUsers(List<User> suggestedUsers, User userWhoSearch, SearchRequest request)
    {
        List<User> res;
            res = UserSearchFiltrator.filterByAge(suggestedUsers, request.getMinAge(), request.getMaxAge());
            res = UserSearchFiltrator.filterByLocation(res, request.getLocationRange(), userWhoSearch);
            res = UserSearchFiltrator.filterByRating(res, request.getRate());
            res = UserSearchFiltrator.filterByTag(res, request.getInterests());
        return res;
    }

    private boolean filterSearchResult(SearchRequest searchRequest, User userWhoSearch, User resultUser) {
        DistanceCalculator.Point userWhoSearchLocation = new DistanceCalculator.Point(searchRequest.getLatitude(), searchRequest.getLongitude());
        DistanceCalculator.Point userLocation = new DistanceCalculator.Point(resultUser.getInformation().getLatitude(), resultUser.getInformation().getLongitude());

        if (searchRequest.getInterests().size() != 0)
            if (Collections.disjoint(resultUser.getInformation().getInterests(), searchRequest.getInterests()))
                return false;
        if (resultUser.getId() == userWhoSearch.getId())
            return false;
        if (distanceCalculator.calculateDistanceTo(userWhoSearchLocation, userLocation) < searchRequest.getLocationRange())
            return false;
        return true;
    }

    private String getSearchedSex(User user) {
        String sex = null;
        if (user.getInformation().getSex().equals("") || user.getInformation().getSexPref().equals(""))
            throw new IllegalArgumentException("sex or sexPrferences are not specified");
        if (user.getInformation().getSexPref().equals("'homosexual'"))
            sex = user.getInformation().getSex();
        if (user.getInformation().getSexPref().equals("heterosexual"))
            sex = (user.getInformation().getSex().equals("man")) ? "'woman'" : "'man'";
        if (user.getInformation().getSexPref().equals("bisexual"))
            sex = "'man, woman'";
        return sex;
    }
}
