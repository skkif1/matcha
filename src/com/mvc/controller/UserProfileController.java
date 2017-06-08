package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserProfileController {

    @RequestMapping(method = RequestMethod.GET)
    public String getUserProfile()
    {
        return "userProfile";
    }

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public void displaySession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Enumeration<String> enu = session.getAttributeNames();

        while (enu.hasMoreElements())
        {
            String name = enu.nextElement();
            System.out.println(name + " "  + session.getAttribute(name).toString());
        }
    }
}
