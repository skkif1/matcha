package com.mvc.controller;

import com.mvc.Entity.JsonResponseWrapper;
import com.mvc.Entity.User;
import com.mvc.Entity.UserInformation;
import com.mvc.config.ApplicationEventListener;
import com.mvc.model.UserInformationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping (value = "/info")
public class UserInformationController {

    private UserInformationManager managerInfo;
    private JsonResponseWrapper json;

    private final String CDN_SERVER_ADDRESS = ApplicationEventListener.CDN_SERVER_ADDRESS;

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

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public @ResponseBody String updateUserInfo(@RequestBody UserInformation userInfo, HttpSession session)
    {
        User user = (User)session.getAttribute("user");
        user.setInformation(userInfo);
        json = managerInfo.updateUser(user);
         return json.toString();
    }

    @RequestMapping(value = "/updatePhoto")
    public @ResponseBody String updateUserPhoto(@RequestParam("files")MultipartFile[] photos)
    {
        User user = new User();
        user.setId(12);
        json = managerInfo.updateSavePhoto(user, photos);
        return json.toString();
    }

    @RequestMapping(value = "/updateEmail", method = RequestMethod.POST)
    public @ResponseBody String updateUser(@RequestParam("email") String email, HttpSession session)
    {
//        User user = (User)session.getAttribute("user");
        User user = new User();
        user.setId(12);
        user.setEmail("sk@gmail.com");
        json = managerInfo.updateUserEmail(email, user);
        return json.toString();
    }

    @RequestMapping(value = "/confirmEmail")
    public ModelAndView cofirmEmailChange(@RequestParam("id") Integer emailOnChangeId, ModelAndView model)
    {
        managerInfo.
        return model;
    }


}
