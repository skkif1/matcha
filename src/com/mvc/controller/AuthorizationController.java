package com.mvc.controller;

import com.mvc.Entity.JsonResponse;
import com.mvc.Entity.JsonResponseWrapper;
import com.mvc.Entity.User;
import com.mvc.model.AuthorizationManager;
import com.mvc.model.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.servlet.ModelAndView;

import javax.enterprise.inject.Produces;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;

@Controller
@RequestMapping("/authorization")
@SessionAttributes("user")
public class AuthorizationController {

    private AuthorizationManager authorizationManager;
    private JsonResponseWrapper jsonResponse;

    public AuthorizationController() {

    }

    @Autowired
    public AuthorizationController(AuthorizationManager authorizationManager, JsonResponseWrapper jsonResponse) {
        this.authorizationManager = authorizationManager;
        this.jsonResponse = jsonResponse;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAuthorization(ModelAndView model) {
        return "authorization";
    }


    @RequestMapping(value = "/signUp", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String signUp(@RequestBody User user, ModelMap model) throws InterruptedException {
        JsonResponseWrapper json = authorizationManager.signUpUser(user);
        model.addAttribute("user", user);
        return json.toString();
    }

    @RequestMapping(value = "/confirm/", method = RequestMethod.POST)
    public String confirmEmail(HttpServletRequest request)
    {
        if (!authorizationManager.confirmEmail((String)request.getParameter("email"), (String)request.getParameter("salt")))
          return "404";
        return "authorization";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String login(@RequestBody User user, ModelMap model) throws InterruptedException {
        JsonResponseWrapper json = authorizationManager.login(user);
        if (json.getAction().equals("confirm"))
        {
            model.addAttribute("user", json.getData());
            json.setData(null);
        }
        return json.toString();
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String reset(@RequestBody User user)
    {
        JsonResponseWrapper json = authorizationManager.resetUserPassword(user);
        return json.toString();
    }

    @RequestMapping(value = "/changeRequest", method = RequestMethod.POST)
    public ModelAndView requestOnchangePassword(HttpServletRequest request, ModelAndView model, HttpSession session)
    {
        String email = request.getParameter("email");
        String salt = request.getParameter("salt");
        if (authorizationManager.requestOnchangePassword(email, salt))
        {
            model.setViewName("changePassword");
            session.setAttribute("email", email);
            session.setAttribute("salt", salt);
        }
        else
        {
            model.setViewName("404");
        }
        return model;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST, produces = "application/json" )
    public @ResponseBody String changePassword(HttpServletRequest request, HttpSession session)
    {
        if (authorizationManager.changePassword((String)request.getParameter("password"), (String)session.getAttribute("email"), (String)session.getAttribute("salt")))
            jsonResponse.setAction("confirm");
        else
            jsonResponse.setAction("error");
        return jsonResponse.toString();
    }
}