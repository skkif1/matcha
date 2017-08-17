package com.matcha.controller;

import com.matcha.entity.User;
import com.matcha.entity.UserPageContext;
import com.matcha.model.AcountManager;
import com.matcha.model.messageBroker.ImessageBroker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/")
public class UserController {

    private ImessageBroker broker;
    private AcountManager acountManager;

    @Autowired
    public UserController(ImessageBroker broker, AcountManager infoManager) {
        this.broker = broker;
        this.acountManager = infoManager;
    }

    @RequestMapping( method = RequestMethod.GET)
    public ModelAndView getUserPage(ModelAndView modelAndView, HttpSession session)
    {
        if (session.getAttribute("user") == null)
            modelAndView.setViewName("authorization");
        else
            modelAndView.setViewName("userPage");
        return modelAndView;
    }

    @RequestMapping(value = "/context", method = RequestMethod.POST)
    public @ResponseBody UserPageContext getUserPageContext(HttpSession session)
    {
        UserPageContext ctx = new UserPageContext();
        User user = (User) session.getAttribute(User.USER_ATTRIBUTE_NAME);
        ctx.setPermissionForSearch(acountManager.checkIfUserEligableForSearch(user));
        return ctx;
    }


}
