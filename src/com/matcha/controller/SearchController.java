package com.matcha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

    @RequestMapping(method = RequestMethod.GET)
    public String getSearch()
    {
        return "search";
    }
}
