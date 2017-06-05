package com.mvc.controller;

import com.mvc.Entity.JsonResponse;
import com.mvc.Entity.JsonResponseWrapper;
import com.mvc.Entity.User;
import com.mvc.model.AuthorizationManager;
import com.mvc.model.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.enterprise.inject.Produces;

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
    public @ResponseBody JsonResponseWrapper signUp(@RequestBody User user) throws InterruptedException {
        JsonResponseWrapper json = authorizationManager.signUpUser(user);
        System.out.println(user.toString());
        System.out.println(json.getAction());
        return (JsonResponse)json;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody JsonResponseWrapper login(@RequestBody User user) throws InterruptedException {
        System.out.println(user.toString());
        JsonResponseWrapper json = authorizationManager.login(user);
        return json;
    }
}
