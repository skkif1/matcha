package com.matcha.model;

import com.matcha.dao.InformationDao;
import com.matcha.entity.User;
import com.matcha.entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UserInformationManager(InformationDao informationDao, JsonResponseWrapper json)
    {
        this.informationDao = informationDao;
        this.json = json;
    }

    public JsonResponseWrapper updateUserInfo(UserInformation userInfo, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        if (user == null)
        {
            json.setStatus("Error");
            json.setData(new ArrayList<String>(Arrays.asList(new String[]{"out of session"})));
            return json;
        }
        informationDao.saveUserInfo(userInfo, user.getId());
        json.setStatus("OK");
        json.setData(new ArrayList<String>(Arrays.asList(new String[]{"Information updated"})));
        return json;
    }

    public JsonResponseWrapper savePhoto(MultipartFile[] photos, HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if (user == null)
        {
            json.setStatus("Error");
            json.setData(new ArrayList<String>(Arrays.asList(new String[]{"out of session"})));
            return json;
        }
        try {
            informationDao.savePhoto(photos, user.getId());
        }catch (IOException ex)
        {
            json.setStatus("Error");
            json.setData("IOException");
        }
        return json;
    }
}
