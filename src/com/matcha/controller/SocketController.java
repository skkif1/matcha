package com.matcha.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
public class SocketController {


    @MessageMapping("/conversation")
    @SendTo("/topic")
    public String hello(SimpMessageHeaderAccessor header)
    {
        return "hello";
    }

}
