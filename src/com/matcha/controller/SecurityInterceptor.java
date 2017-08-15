package com.matcha.controller;


import com.matcha.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        if(session == null || session.getAttribute(User.USER_ATTRIBUTE_NAME) == null)
        {
            System.out.println(httpServletRequest.getServletPath());
            System.out.println(httpServletRequest.getContextPath());
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            System.out.println("rejected");
            httpServletResponse.sendRedirect("/matcha");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
