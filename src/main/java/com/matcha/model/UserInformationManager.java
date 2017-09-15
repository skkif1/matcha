package com.matcha.model;

import com.matcha.dao.InformationDao;
import com.matcha.dao.UserDao;
import com.matcha.entity.AcountPageContext;
import com.matcha.entity.User;
import com.matcha.entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


@Component
public class UserInformationManager {

    private InformationDao informationDao;
    private JsonResponseWrapper json;
    private UserDao userDao;

    @Autowired
    public UserInformationManager(InformationDao informationDao, JsonResponseWrapper json, UserDao userDao) {
        this.informationDao = informationDao;
        this.json = json;
        this.userDao = userDao;
    }

    public JsonResponseWrapper updateUserInfo(UserInformation userInfo, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        informationDao.saveUserInfo(userInfo, user.getId());
        userInfo = informationDao.getUserInfoByUserId(user.getId());
        user.setInformation(userInfo);
        session.setAttribute("user", user);
        json.setStatus("OK");
        json.setData(new ArrayList<String>(Arrays.asList(new String[]{"Information updated"})));
        return json;
    }

    public JsonResponseWrapper updateUserData(User user, HttpSession session) {
        JsonResponseWrapper json = new JsonResponseWrapper();
        User currentUser = (User) session.getAttribute("user");
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setEmail(user.getEmail());
        userDao.updateUser(currentUser);
        json.setStatus("OK");
        return json;
    }

    public UserInformation getUserInfo(User user) {
        UserInformation info = informationDao.getUserInfoByUserId(user.getId());
        return info;
    }

    public JsonResponseWrapper savePhoto(MultipartFile[] photos, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            json.setStatus("Error");
            json.setData(new ArrayList<String>(Arrays.asList(new String[]{"out of session"})));
            return json;
        }
        try {
            informationDao.savePhoto(photos, user.getId());
            json.setStatus("OK");
            user.setInformation(informationDao.getUserInfoByUserId(user.getId()));
        } catch (Exception ex) {
            json.setStatus("Error");
            json.setData(new ArrayList<String>(Arrays.asList(new String[]{"You allready have photo with such name"})));
        }
        return json;
    }


    public String getPhotoById(Integer photoId) {
        return informationDao.getPhotoById(photoId);
    }

    public JsonResponseWrapper delletePhoto(String path, HttpSession session) {
        JsonResponseWrapper json = new JsonResponseWrapper();
        User user = (User) session.getAttribute("user");
        informationDao.deletePhoto(path, user.getId());
        if(user.getInformation().getAvatar() != null && user.getInformation().getAvatar().equals(path))
            informationDao.saveAvatar(null, user.getId());
        json.setStatus("OK");
        return json;
    }

    public JsonResponseWrapper setAvatar(String path, HttpSession session) {
        JsonResponseWrapper json = new JsonResponseWrapper();
        User user = (User) session.getAttribute("user");
        informationDao.saveAvatar(path, user.getId());
        json.setStatus("OK");
        return json;
    }

    public AcountPageContext getAcountPageContext(Integer userId, Integer visitorId) {
        AcountPageContext ctx = new AcountPageContext();
        ctx.setLiked(informationDao.checkIfUserLiked(userId, visitorId));
        ctx.setMatched(informationDao.checkIfMatchedWith(visitorId, userId));
        ctx.setBlackList(informationDao.ifUserInBlackList(visitorId, userId));
        ctx.setBlocked(informationDao.ifUserInBlackList(userId, visitorId));
        return ctx;
    }
}
