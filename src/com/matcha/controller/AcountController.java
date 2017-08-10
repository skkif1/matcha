package com.matcha.controller;

import com.matcha.entity.User;
import com.matcha.entity.UserInformation;
import com.matcha.model.AcountManager;
import com.matcha.model.HistoryManager;
import com.matcha.model.JsonResponseWrapper;
import com.matcha.model.UserInformationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/acount")
public class AcountController {

    private UserInformationManager userInfo;
    private AcountManager acountManager;
    private HistoryManager historytManager;


    @Autowired
    public AcountController(UserInformationManager userInfo, AcountManager acountManager, HistoryManager historytManager) {
        this.userInfo = userInfo;
        this.acountManager = acountManager;
        this.historytManager = historytManager;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String getAcount(@PathVariable("id") Integer id, HttpSession session)
    {
        User visitor = (User) session.getAttribute("user");
        if (id == visitor.getId())
            return "redirect:/";
        if (historytManager.requisterVisit(visitor.getId(), id))
            return "acountPage";
        return "404";
    }


    @RequestMapping(value = "/like/{userId}")
    public @ResponseBody String likeUser(@PathVariable("userId") Integer userId, HttpSession session)
    {
        JsonResponseWrapper json = acountManager.likeUser(userId, session);
        return json.toString();
    }

    @RequestMapping(value = "/checkConnection/{userId}")
    public @ResponseBody String checkConnection(@PathVariable("userId") Integer userToCheck, HttpSession session)
    {
        JsonResponseWrapper json = acountManager.likeUser(userToCheck, session);
        return json.toString();
    }
}
