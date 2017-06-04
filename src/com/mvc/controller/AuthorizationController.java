package com.mvc.controller;

import com.mvc.Entity.JsonResponse;
import com.mvc.Entity.JsonResponseWrapper;
import com.mvc.Entity.User;
import com.mvc.model.AuthorizationManager;
import com.mvc.model.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/authorization")
public class AuthorizationController {

    private AuthorizationManager authorizationManager;

    public AuthorizationController() {
    }

    @Autowired
    public AuthorizationController(AuthorizationManager authorizationManager) {
        this.authorizationManager = authorizationManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAuthorization(ModelAndView model) {
        return "authorization";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody JsonResponseWrapper signUp(@RequestBody User user)
    {
        JsonResponseWrapper json = authorizationManager.signUpUser(user);
        System.out.println(user.toString());
        return json;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody JsonResponseWrapper login(@RequestBody User user)
    {
        System.out.println(user.toString());
        JsonResponseWrapper json = authorizationManager.login(user);
        return json;
    }
}
