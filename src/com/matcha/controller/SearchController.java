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
    public String getSearch()
    {
        return "search";
    }

    @RequestMapping(value = "/searchForUsers", method = RequestMethod.POST)
    public @ResponseBody JsonResponseWrapper searchForUsers(@RequestBody SearchRequest request, HttpSession session)
    {
        JsonResponseWrapper json = new JsonResponseWrapper();
        List<User> foundUsers  = acountManager.searchForUsers(request, session);

        json.setStatus("OK");
        json.setData(foundUsers);

        System.out.println(request);
        return json;
    }
}