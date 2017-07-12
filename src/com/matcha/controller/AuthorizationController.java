package com.matcha.controller;

import com.matcha.entity.User;
import com.matcha.model.AuthorizationManager;
import com.matcha.model.JsonResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public @ResponseBody String requestOnPasswordReset(@RequestBody User user)
    {
        JsonResponseWrapper json = authorizationManager.resetUserPassword(user);
        return json.toString();
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public ModelAndView changePasswordRequest(HttpServletRequest request, ModelAndView model, HttpSession session)
    {
        String email = request.getParameter("email");
        String salt = request.getParameter("salt");
        if (authorizationManager.changePasswordRequest(email, salt))
        {
            model.setViewName("passwordReset");
            session.setAttribute("email", email);
        }
        else
            model.setViewName("404");
        return model;
    }


    @RequestMapping(value = "/newPassword", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String newPassword(@RequestParam("password") String password, HttpSession session)
    {
        System.out.println(password);
        JsonResponseWrapper ajax = authorizationManager.changePassword(password, session);
        return ajax.toString();
    }
}
