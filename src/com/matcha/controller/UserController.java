package com.matcha.controller;

import com.matcha.entity.Message;
import com.matcha.entity.PageContext;
import com.matcha.entity.User;
import com.matcha.model.messageBroker.ApplicationMessageBroker;
import com.matcha.model.messageBroker.ImessageBroker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/")
public class UserController {

    private ImessageBroker broker;


    @Autowired
    public UserController(ImessageBroker broker) {
        this.broker = broker;
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

    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String test(HttpSession session)
    {
        System.out.println("test");
        broker.consumeMessage(new TextMessage("Hello world!!"), (User) session.getAttribute("user"));
        return "test";
    }

}
