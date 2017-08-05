package com.matcha.controller;

import com.matcha.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/")
public class UserController {

    @RequestMapping( method = RequestMethod.GET)
    public ModelAndView getUserPage(ModelAndView modelAndView, HttpSession session)
    {
        if (session.getAttribute("user") == null)
            modelAndView.setViewName("authorization");
        else
        modelAndView.setViewName("userPage");
        return modelAndView;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test()
    {
        return "test";
    }
}
