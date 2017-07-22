package com.matcha.controller;

import com.matcha.entity.User;
import com.matcha.entity.UserInformation;
import com.matcha.model.JsonResponseWrapper;
import com.matcha.model.UserInformationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping(value = "/info")
public class UserInformationController {

    private UserInformationManager infoManager;

    @Autowired
    public UserInformationController(UserInformationManager infoManager) {
        this.infoManager = infoManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getUserInfo(ModelAndView model, HttpSession session)
    {
        model.setViewName("userInformation");
        UserInformation userInfo = infoManager.getUserInfo((User) session.getAttribute("user"));
        session.setAttribute("info", userInfo);
        model.addObject("information", userInfo);
        model.addObject("user", session.getAttribute("user"));
        return model;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public @ResponseBody String changeUserData(@RequestBody User user, HttpSession session)
    {
        JsonResponseWrapper json = infoManager.updateUserData(user, session);
        return json.toString();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String updateUserInfo(@RequestBody UserInformation userinfo, HttpSession session, HttpServletResponse resp) throws IOException {
        JsonResponseWrapper ajax = infoManager.updateUserInfo(userinfo, session);
        return ajax.toString();
    }

    @RequestMapping(value = "/updatePhoto")
    public @ResponseBody String updateUserPhoto(@RequestParam("files")MultipartFile[] photos, HttpSession session)
    {
        JsonResponseWrapper ajax = infoManager.savePhoto(photos, session);
        return ajax.toString();
    }

    @RequestMapping(value = "/dellPhoto")
    public @ResponseBody String deletePhoto(@RequestParam("path") String path, HttpSession session)
    {
        JsonResponseWrapper ajax = infoManager.delletePhoto(path, session);
        return ajax.toString();
    }

    @RequestMapping(value = "/setAvatar")
    public @ResponseBody String setAvatar(@RequestParam("path") String path, HttpSession session)
    {
        JsonResponseWrapper json = infoManager.setAvatar(path, session);
                return json.toString();
    }

    @RequestMapping(value = "/getInfo")
    public @ResponseBody String getUserInfo(HttpSession session)
    {
        JsonResponseWrapper json = new JsonResponseWrapper();
        UserInformation userInfo = infoManager.getUserInfo((User) session.getAttribute("user"));
        if (userInfo != null)
        {
            json.setStatus("OK");
            json.setData(userInfo);
        }
        return json.toString();
    }
}
