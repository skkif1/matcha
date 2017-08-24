package com.matcha.controller;

import com.matcha.entity.User;
import com.matcha.model.AcountManager;
import com.matcha.model.JsonResponseWrapper;
import com.matcha.model.UserInformationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;

@Controller
@RequestMapping(value = "/acount")
public class AcountController {

    private AcountManager acountManager;


    @Autowired
    public AcountController(AcountManager acountManager) {
        this.acountManager = acountManager;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String getAcount(@PathVariable("id") Integer id, HttpSession session)
    {
        User visitor = (User) session.getAttribute("user");
        System.out.println(acountManager.checkIfUserEligableForSearch(visitor));
        if (id == visitor.getId() || !acountManager.checkIfUserEligableForSearch(visitor))
            return "redirect:/";
        if (acountManager.requisterVisit(visitor, id))
            return "acountPage";
        return "404";
    }

    @RequestMapping(value = "/like/{userId}")
    public @ResponseBody String likeUser(@PathVariable("userId") Integer userId, HttpSession session)
    {
        JsonResponseWrapper json = acountManager.likeUser(userId, session);
        return json.toString();
    }

    @RequestMapping(value = "/dislike/{userId}")
    public @ResponseBody String dislikeUser(@PathVariable("userId") Integer authorId, HttpSession session)
    {
        JsonResponseWrapper jsonResponseWrapper = acountManager.dislikeUser(authorId, session);
        return jsonResponseWrapper.toString();
    }

    @RequestMapping(value = "/blackList/{userId}")
    public @ResponseBody String addToBlackList(@PathVariable("userId") Integer userId, HttpSession session)
    {
        JsonResponseWrapper json = acountManager.addToBlackList(userId, session);
        return json.toString();
    }

    @RequestMapping(value = "/reportAsFake/{userId}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void reportAsFake(@PathVariable("userId") Integer id)
    {
        acountManager.saveFakeAcount(id);
    }

    @RequestMapping(value = "/checkConnection/{userId}")
    public @ResponseBody String checkConnection(@PathVariable("userId") Integer userToCheck, HttpSession session)
    {
        JsonResponseWrapper json = acountManager.likeUser(userToCheck, session);
        return json.toString();
    }



}