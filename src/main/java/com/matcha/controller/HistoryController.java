package com.matcha.controller;

import com.matcha.entity.HistoryPageContext;
import com.matcha.entity.User;
import com.matcha.model.AcountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String getHistory() {
        return "history";
    }

    @RequestMapping(value = "/historyContext", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    HistoryPageContext getHistoryContext(HttpSession session) {
        HistoryPageContext ctx = acountManager.getHistoryPageContext(session);
        return ctx;
    }

    @RequestMapping(value = "/read/{eventType}")
    @ResponseStatus(HttpStatus.OK)
    public void readEvents(@PathVariable("eventType") String evenType, HttpSession session) {
        User user = (User) session.getAttribute(User.USER_ATTRIBUTE_NAME);
        acountManager.readEvents(evenType, user);
    }
}
