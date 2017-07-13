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
        model.addObject("user", session.getAttribute("user"));
        return model;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String updateUserInfo(@RequestBody UserInformation userinfo, HttpSession session, HttpServletResponse resp) throws IOException {
        JsonResponseWrapper ajax = infoManager.updateUserInfo(userinfo, session);
        return ajax.toString();
    }

    @RequestMapping(value = "/updatePhoto")
    public @ResponseBody String updateUserPhoto(@RequestParam("files")MultipartFile[] photos, HttpSession session)
    {
        System.out.println("update");
        JsonResponseWrapper ajax = infoManager.savePhoto(photos, session);
        return ajax.toString();
    }
}
