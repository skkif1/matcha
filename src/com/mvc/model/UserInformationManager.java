package com.mvc.model;

import com.mvc.DAO.PhotoDao;
import com.mvc.DAO.UserDao;
import com.mvc.DAO.UserInformationDao;
import com.mvc.Entity.JsonResponseWrapper;
import com.mvc.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserInformationManager {

    private JsonResponseWrapper json;
    private UserInformationDao informationDao;
    private UserDao userDao;
    private PhotoDao photoDao;
    private EmailSender emailSender;

    @Autowired
    public UserInformationManager(JsonResponseWrapper json, UserInformationDao informationDao, UserDao userDao, PhotoDao photoDao, EmailSender emailSender) {
        this.json = json;
        this.informationDao = informationDao;
        this.userDao = userDao;
        this.photoDao = photoDao;
        this.emailSender = emailSender;
    }

    public JsonResponseWrapper updateUser(User informedUser) {

        informationDao.saveUserInformation(informedUser);
        userDao.updateUser(informedUser);
        return json;
    }

    public JsonResponseWrapper updateSavePhoto(User user, MultipartFile[] files)
    {
        try {
            photoDao.savePhoto(files, user.getId());
        }catch (IOException ex)
        {
            System.out.println(ex.getMessage());
            json.setAction("error");
            return json;
        }
        json.setAction("confirm");
        return json;
    }

    public JsonResponseWrapper updateUserEmail(String email, User user)
    {
        if (email.equals(user.getEmail()))
        {
            json.setAction("error");
            json.setData(Arrays.asList(new String[]{"This is your actuall email address"}));
        }else
        {
            Map<String, Object> map = new HashMap<>();
            Integer res = userDao.addEmailUpdateRequest(user);
            map.put("id", res);
            user.setEmail("skkif1@gmail.com");
            emailSender.send("changeEmailConfirmation.ftl", "confirm email", user.getEmail(), map);
            json.setAction("confirm");
            json.setData(Arrays.asList(new String[]{"Confirmation email was send to your email address. Email will be changed after confirmation"}));
        }
        return json;
    }

    public Boolean checkEmailConfirmation(Integer id)
    {
        String email = userDao.getEmailUpdateRequest(id);
        User user = userDao.getUserByEmail(email);
        user.setEmail();
    }
}
