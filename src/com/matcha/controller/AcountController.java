package com.matcha.controller;

import com.matcha.entity.User;
import com.matcha.entity.UserInformation;
import com.matcha.model.UserInformationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/acount")
public class AcountController {

    private UserInformationManager userInfo;

    @Autowired
    public AcountController(UserInformationManager userInfo) {
        this.userInfo = userInfo;
    }


    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String getAcount(@PathVariable("id") Integer id)
    {
        System.out.println(id);
        return "acountPage";
    }

}
