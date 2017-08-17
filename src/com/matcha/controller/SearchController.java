package com.matcha.controller;

import com.matcha.entity.SearchRequest;
import com.matcha.entity.User;
import com.matcha.model.AcountManager;
import com.matcha.model.JsonResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

    private AcountManager acountManager;

    @Autowired
    public SearchController(AcountManager acountManager) {
        this.acountManager = acountManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getSearch(HttpSession session)
    {
        User user = (User) session.getAttribute(User.USER_ATTRIBUTE_NAME);
        if (acountManager.checkIfUserEligableForSearch(user))
            return "search";
        else
            return "redirect:/";
    }

    @RequestMapping(value = "/suggestions", method = RequestMethod.GET)
    public String getSuggetstions(HttpSession session)
    {
        User user = (User) session.getAttribute(User.USER_ATTRIBUTE_NAME);
        if (acountManager.checkIfUserEligableForSearch(user))
            return "suggestions";
        else
            return "redirect:/";
    }

    @RequestMapping(value = "/searchForUsers", method = RequestMethod.POST)
    public @ResponseBody JsonResponseWrapper searchForUsers(@RequestBody SearchRequest request, HttpSession session)
    {
        JsonResponseWrapper json = new JsonResponseWrapper();
        List<User> foundUsers  = acountManager.searchForUsers(request, session);
        json.setStatus("OK");
        json.setData(foundUsers);
        return json;
    }


//    @RequestMapping(value = "/suggest", method = RequestMethod.POST)
//    public @ResponseBody JsonResponseWrapper suggestUserProfiles(HttpSession session)
//    {
//        JsonResponseWrapper json = new JsonResponseWrapper();
//        List<User> suggestedUsers = acountManager.searchForUsers();
//    }
}