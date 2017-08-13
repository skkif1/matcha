package com.matcha.controller;

import com.matcha.entity.SearchRequest;
import com.matcha.model.JsonResponseWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

    @RequestMapping(method = RequestMethod.GET)
    public String getSearch()
    {
        return "search";
    }

    @RequestMapping(value = "/getUsers", method = RequestMethod.POST)
    public @ResponseBody JsonResponseWrapper getUsers(@RequestBody SearchRequest request)
    {
        System.out.println(request);
        System.out.println("OK");
        return new JsonResponseWrapper();
    }
}
