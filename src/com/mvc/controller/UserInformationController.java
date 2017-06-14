package com.mvc.controller;

import com.mvc.Entity.JsonResponseWrapper;
import com.mvc.Entity.User;
import com.mvc.Entity.UserInformation;
import com.mvc.model.UserInformationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping (value = "/info")
public class UserInformationController {

    private UserInformationManager managerInfo;
    private JsonResponseWrapper json;


    @Autowired
    public UserInformationController(UserInformationManager managerInfo, JsonResponseWrapper json) {
        this.managerInfo = managerInfo;
        this.json = json;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getUserInformation()
    {
        return "userInformation";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody String apdateUserInfo(@RequestBody UserInformation userInfo, HttpSession session)
    {
        User user = (User)session.getAttribute("user");
        user.setInformation(userInfo);
        json = managerInfo.updateUser(user);
         return json.toString();
    }
}
