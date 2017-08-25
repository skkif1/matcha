package com.matcha.model;

import com.matcha.dao.ChatDao;
import com.matcha.dao.InformationDao;
import com.matcha.dao.UserDao;
import com.matcha.entity.*;
import com.matcha.model.messageBroker.ImessageBroker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AcountManager {

    private InformationDao infoDao;
    private ImessageBroker messageBroker;
    private UserDao userDao;
    private ChatDao chatDao;

    @Autowired
    public AcountManager(InformationDao infoDao, ImessageBroker messageBroker, UserDao userDao, ChatDao chatDao) {
        this.infoDao = infoDao;
        this.messageBroker = messageBroker;
        this.userDao = userDao;
        this.chatDao = chatDao;
    }

    public JsonResponseWrapper likeUser(Integer userId, HttpSession session) {
        JsonResponseWrapper json = new JsonResponseWrapper();
        User author = (User) session.getAttribute("user");

            infoDao.likeUser(userId, author.getId());
            infoDao.incrementRate(userId);
            Notification notification = new Notification();
            User user = userDao.getUserById(userId);

            if (infoDao.checkIfUserLiked(author.getId(), userId)) {
                infoDao.requisterMathedConnection(author.getId(), userId);
                infoDao.incrementRate(userId);
                infoDao.incrementRate(author.getId());
                notification.setCategory("history");
                notification.setBody("now you are matched with " + author.getFirstName() + " " + author.getLastName());
                messageBroker.consumeMessage(new TextMessage(notification.toString()), userId.toString(), TextSocketHandler.USER_ENDPOINT);
            }

            notification.setBody(author.getFirstName() + " " + author.getLastName() + " liked your profile!");
            notification.setCategory("history");
            json.setStatus("OK");
            messageBroker.consumeMessage(new TextMessage(notification.toString()), userId.toString(), TextSocketHandler.USER_ENDPOINT);
        return json;
    }

    public JsonResponseWrapper dislikeUser(Integer userId, HttpSession session) {
        JsonResponseWrapper json = new JsonResponseWrapper();
        User author = (User) session.getAttribute(User.USER_ATTRIBUTE_NAME);
        infoDao.removeLike(author.getId(), userId);
        infoDao.removeConnection(author.getId(), userId);
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

    public Boolean requisterVisit(User visitor, Integer userId) {
        if (infoDao.getUserInfoByUserId(userId) == null)
            return false;
        else {
            if (infoDao.saveVisit(visitor.getId(), userId)) {
                Notification notification = new Notification();
                User user = userDao.getUserById(userId);
                notification.setCategory("visit");
                notification.setBody(visitor.getFirstName() + " " + visitor.getLastName() + " visit your prfile!");
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
        List<User> visitedUsers = infoDao.getUserVisits(user.getId());
        ctx.setVisitors(visitors);
        ctx.setLikes(likeAuthors);
        ctx.setVisited(visitedUsers);
        ctx.setLastConnections(connectdUsers);
        Map<String, Integer> eventsNumber = new HashMap<>();
        infoDao.getNewEventsNumber(eventsNumber, user.getId());
        ctx.setNewConnections(eventsNumber.get("connections"));
        ctx.setNewLikes(eventsNumber.get("likes"));
        ctx.setNewVisitors(eventsNumber.get("visitors"));

        return ctx;
    }

    public void readEvents(String eventType, User user)
    {
        switch (eventType)
        {
            case "visitors":
                infoDao.readNewVisits(user.getId());
                break;

            case "likes":
                infoDao.readNewLike(user.getId());
                break;

            case "connections":
                infoDao.readNewConnections(user.getId());
                break;
        }
    }

    public Boolean checkIfUserEligableForSearch(User user)
    {
        UserInformation info = infoDao.getUserInfoByUserId(user.getId());
        if (info == null || info.getSex() == null || info.getAvatar() == null)
            return false;
        return true;
    }

    public UserPageContext getUserContext(User user)
    {
        UserPageContext ctx = new UserPageContext();
        UserInformation info = infoDao.getUserInfoByUserId(user.getId());
        if (info == null || info.getSex() == null || info.getAvatar() == null)
            ctx.setPermissionForSearch(false);
        else
            ctx.setPermissionForSearch(true);
        ctx.setNumberOfNewEvents(infoDao.getNewEventsNumber(user.getId()));
        ctx.setNumberOfNewMessages(chatDao.getAllNewMessages(user.getId()));
        return ctx;
    }



    public List<User> searchForUsers(SearchRequest searchParams, HttpSession session) {
        String searchedSex;
        User userWhoSearch = (User) session.getAttribute(User.USER_ATTRIBUTE_NAME);

        System.out.println(searchParams);
        searchedSex = getSearchedSex(userWhoSearch);
        List<User> foundUsers = infoDao.searchUsersWith(searchedSex, userWhoSearch.getInformation().getSexPref(),
                searchParams.getMinAge(), searchParams.getMaxAge(), searchParams.getRate());

        foundUsers = filterSuggestedUsers(foundUsers, userWhoSearch, searchParams);
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
            removeUsersInBlackList(res, userWhoSearch);
        return res;
    }


    public void saveFakeAcount(Integer userId)
    {
        userDao.addFakeAcount(userId);
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

    private void removeUsersInBlackList(List<User> listOfUsers, User userWhoSearch)
    {
        listOfUsers.removeIf(user -> infoDao.ifUserInBlackList(userWhoSearch.getId(), user.getId()));
    }
}
