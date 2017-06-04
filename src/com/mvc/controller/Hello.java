package com.mvc.controller;

import com.mvc.DAO.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class Hello {

    private UserDao userDao;

    public Hello() {
    }

    @Autowired
    public Hello(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String toHello(ModelMap model)
    {
        return "hello";
    }
}
