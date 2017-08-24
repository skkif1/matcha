package com.matcha.controller;

import com.matcha.entity.Conversation;
import com.matcha.entity.Message;
import com.matcha.entity.User;
import com.matcha.model.IChat;
import com.matcha.model.JsonResponseWrapper;
import com.matcha.model.messageBroker.ImessageBroker;
import com.mysql.cj.api.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.UserDestinationMessageHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping(value = "/chat")
public class ChatController {


    private IChat chatManager;



    public ChatController() {
    }

    @Autowired
    public ChatController(IChat chatManager) {
        this.chatManager = chatManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getChat(HttpSession session)
    {
        return "chat";
    }

    @RequestMapping(value = "/{partnerId}", method = RequestMethod.GET)
    public String createConversation(@PathVariable("partnerId") Integer id,  HttpSession session)
    {
        chatManager.createConversation(id, session);
        return "redirect:/chat";
    }

    @RequestMapping(value = "/conversationList", method = RequestMethod.POST)
    public @ResponseBody JsonResponseWrapper getConversationList(HttpSession session)
    {
        JsonResponseWrapper ajax = chatManager.getConversationList(session);
        session.setAttribute("conversationList", ajax.getData());
        return ajax;
    }

    @RequestMapping(value = "/conversation", method = RequestMethod.POST)
    public @ResponseBody JsonResponseWrapper getConversationMessages(@RequestParam Integer id, HttpSession session)
    {
        JsonResponseWrapper ajax = chatManager.getConversationMessages(id, 0, session);
        ArrayList<Conversation> conv = (ArrayList) session.getAttribute("conversationList");
        conv.forEach(conversation ->
        {
            if (conversation.getId() == id)
                session.setAttribute("currentConversation", conversation);
        });
        return ajax;
    }

    @RequestMapping(value = "/conversation/{offset}")
    public @ResponseBody JsonResponseWrapper getPreviousMessages(@RequestParam Integer conversationId,
                                                                 @PathVariable("offset") Integer messageOnView,
                                                                 HttpSession session)
    {
        JsonResponseWrapper ajax = chatManager.getConversationMessages(conversationId, messageOnView, session);
        ajax.setStatus("OK");
        return ajax;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody JsonResponseWrapper sendMessage(@RequestBody Message message, HttpSession session)
    {
        JsonResponseWrapper ajax = new JsonResponseWrapper();
        ajax.setStatus("Error");
        Conversation current  = (Conversation) session.getAttribute("currentConversation");
        message.setConversationId(current.getId());
        message.setReciver(current.getPartner().getId());
        message.setAuthor(current.getHolder().getId());

        if(chatManager.sendMessage(message))
        {
            ajax.setStatus("OK");
            ajax.setData(message);
        }
        return ajax;
    }

    @RequestMapping(value = "/read")
    @ResponseStatus(HttpStatus.OK)
    public void readMessage(@RequestParam("messageId") Integer messageId, HttpSession session)
    {
        User user = (User) session.getAttribute(User.USER_ATTRIBUTE_NAME);
        chatManager.readMessage(messageId, user);
    }
}
