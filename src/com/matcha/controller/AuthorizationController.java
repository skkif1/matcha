package com.matcha.controller;


import com.matcha.entity.User;
import com.matcha.model.AuthorizationManager;
import com.matcha.model.JsonResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Controller
@RequestMapping(value = "/authorization")
public class AuthorizationController {

    private AuthorizationManager authorizationManager;

    public AuthorizationController() {
    }

    @Autowired
    public AuthorizationController(AuthorizationManager authorizationManager) {
        this.authorizationManager = authorizationManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String authorizationPage()
    {
        return "authorization";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public @ResponseBody String signUp(@RequestBody User user) throws InvalidKeySpecException, NoSuchAlgorithmException {

        JsonResponseWrapper ajaxResponse = authorizationManager.signUpUser(user);
        return ajaxResponse.toString();
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirmUserRegistration(HttpServletRequest request)
    {
        if(authorizationManager.confirmEmail(request.getParameter("email"), request.getParameter("salt")))
        {
            return "redirect:/authorization";
        }
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody String loginUser(@RequestBody User user, HttpSession session) throws InvalidKeySpecException, NoSuchAlgorithmException {
        JsonResponseWrapper ajaxResponse = authorizationManager.logInUser(user, session);
        return ajaxResponse.toString();
    }


}
