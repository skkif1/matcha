package com.matcha.controller;

import com.matcha.entity.HistoryPageContext;
import com.matcha.model.AcountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/history")
public class HistoryController {

    private AcountManager acountManager;

    @Autowired
    public HistoryController(AcountManager acountManager) {
        this.acountManager = acountManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getHistory()
    {
        return "history";
    }

    @RequestMapping(value = "/historyContext", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody HistoryPageContext getHistoryContext(HttpSession session)
    {
        HistoryPageContext ctx = acountManager.getHistoryPageContext(session);
        return ctx;
    }
}
